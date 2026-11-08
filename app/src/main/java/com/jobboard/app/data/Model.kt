package com.jobboard.app.data

enum class JobStatus(val label: String, val step: Int) {
    Requested("Requested", 0),
    Accepted("Accepted", 1),
    Scheduled("Scheduled", 2),
    InProgress("In Progress", 3),
    Completed("Completed", 4),
    OnHold("On Hold", -1),
    Canceled("Canceled", -1);

    companion object {
        fun fromName(name: String): JobStatus =
            entries.find { it.name == name } ?: Requested
    }
}

enum class JobPriority(val label: String, val order: Int) {
    Emergency("Emergency", 0),
    Soon("Soon", 1),
    Routine("Routine", 2);
}

data class Job(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val priority: JobPriority,
    val status: JobStatus,
    val streetName: String,
    val fullAddress: String,
    val preferredDate: String?,
    val timeSlot: String?,
    val claimed: Boolean
)
