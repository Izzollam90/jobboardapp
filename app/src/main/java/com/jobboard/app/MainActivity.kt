package com.jobboard.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobboard.app.data.JobRepository
import com.jobboard.app.settings.SettingsRepository
import com.jobboard.app.theme.JobBoardTheme
import com.jobboard.app.ui.JobBoardApp

private val Context.dataStore by preferencesDataStore("jobs_state")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingsRepo = SettingsRepository(applicationContext)
        val jobRepo = JobRepository(applicationContext.dataStore)
        setContent {
            val settings by settingsRepo.settings.collectAsStateWithLifecycle()
            JobBoardTheme(accentHex = settings.accentHex) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    JobBoardApp(settingsRepo = settingsRepo, jobRepo = jobRepo)
                }
            }
        }
    }
}
