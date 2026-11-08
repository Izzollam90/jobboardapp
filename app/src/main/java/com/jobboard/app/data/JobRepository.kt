package com.jobboard.app.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class JobRepository(private val dataStore: DataStore<Preferences>) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val overridesKey = stringPreferencesKey("claimed_state")

    private val _jobs = MutableStateFlow(SampleData.jobs())
    val jobs: StateFlow<List<Job>> = _jobs.asStateFlow()

    init {
        scope.launch {
            val saved = dataStore.data.first()[overridesKey] ?: ""
            if (saved.isNotBlank()) applyOverrides(saved)
        }
    }

    val openBoard: List<Job>
        get() = _jobs.value
            .filter { !it.claimed && it.status == JobStatus.Requested }
            .sortedWith(compareBy({ it.priority.order }, { it.preferredDate ?: "" }))

    val myJobs: List<Job>
        get() = _jobs.value.filter { it.claimed }
            .sortedWith(compareBy({ it.status.step < 0 }, { it.preferredDate ?: "" }))

    fun get(id: String): Job? = _jobs.value.find { it.id == id }

    fun claim(id: String) {
        update(id) { it.copy(status = JobStatus.Accepted, claimed = true) }
    }

    fun advance(id: String) {
        update(id) { j ->
            when (j.status) {
                JobStatus.Accepted -> j.copy(status = JobStatus.Scheduled)
                JobStatus.Scheduled -> j.copy(status = JobStatus.InProgress)
                JobStatus.InProgress -> j.copy(status = JobStatus.Completed)
                else -> j
            }
        }
    }

    fun release(id: String) {
        update(id) { it.copy(status = JobStatus.Requested, claimed = false) }
    }

    private fun update(id: String, f: (Job) -> Job) {
        _jobs.value = _jobs.value.map { if (it.id == id) f(it) else it }
        persist()
    }

    private fun persist() {
        val state = _jobs.value
            .filter { it.claimed }
            .joinToString(";") { "${it.id}=${it.status.name}" }
        scope.launch { dataStore.edit { it[overridesKey] = state } }
    }

    private fun applyOverrides(saved: String) {
        val map = saved.split(";").mapNotNull { part ->
            val kv = part.split("=", limit = 2)
            if (kv.size == 2) kv[0] to JobStatus.fromName(kv[1]) else null
        }.toMap()
        _jobs.value = _jobs.value.map { j ->
            map[j.id]?.let { j.copy(status = it, claimed = true) } ?: j
        }
    }
}
