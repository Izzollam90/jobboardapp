package com.jobboard.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jobboard.app.data.Job
import com.jobboard.app.data.JobStatus

@Composable
fun JobDetailScreen(
    job: Job,
    onAdvance: (String) -> Unit,
    onRelease: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Eyebrow(job.id)
        Spacer(Modifier.height(4.dp))
        Text(
            job.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Row {
            StatusPill(job.status)
            Spacer(Modifier.padding(6.dp))
            PriorityPill(job.priority)
        }
        Spacer(Modifier.height(20.dp))
        StatusTracker(job.status)
        Spacer(Modifier.height(24.dp))

        DetailRow("Category", job.category)
        DetailRow("Address", if (job.claimed) job.fullAddress else "${job.streetName} (exact address after claim)")
        DetailRow("Preferred date", job.preferredDate ?: "No date set")
        DetailRow("Time slot", job.timeSlot ?: "No slot set")
        DetailRow("Description", job.description)

        Spacer(Modifier.height(28.dp))
        if (job.claimed && job.status.step in 1..3) {
            Button(
                onClick = { onAdvance(job.id) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                val next = when (job.status) {
                    JobStatus.Accepted -> "Mark scheduled"
                    JobStatus.Scheduled -> "Start job"
                    JobStatus.InProgress -> "Mark completed"
                    else -> "Next"
                }
                Text(next, style = MaterialTheme.typography.labelLarge)
            }
            Spacer(Modifier.height(10.dp))
        }
        if (job.claimed && job.status != JobStatus.Completed) {
            OutlinedButton(
                onClick = { onRelease(job.id) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("Release back to board")
            }
        }
        Spacer(Modifier.height(48.dp))
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.5.dp.value.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(2.dp))
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}
