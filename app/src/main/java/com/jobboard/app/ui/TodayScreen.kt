package com.jobboard.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jobboard.app.data.Job
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TodayScreen(
    myJobs: List<Job>,
    openCount: Int,
    onOpen: (String) -> Unit
) {
    val today = LocalDate.now()
    val todayStr = today.format(DateTimeFormatter.ISO_LOCAL_DATE)
    val todaysJobs = myJobs.filter { it.preferredDate == todayStr }
    val upcoming = myJobs.filter { it.preferredDate != null && it.preferredDate != todayStr }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Eyebrow("Today")
        Spacer(Modifier.height(4.dp))
        Text(
            today.format(DateTimeFormatter.ofPattern("EEEE, MMM d", Locale.getDefault())),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "${todaysJobs.size} job(s) today \u00B7 $openCount open on the board",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))
        LazyColumn(
            contentPadding = PaddingValues(bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    "Scheduled today",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            if (todaysJobs.isEmpty()) {
                item {
                    Text(
                        "Nothing scheduled today.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            items(todaysJobs, key = { it.id }) { job -> JobRow(job, onOpen) }
            item {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Coming up",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            items(upcoming, key = { it.id }) { job -> JobRow(job, onOpen) }
        }
    }
}

@Composable
private fun JobRow(job: Job, onOpen: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen(job.id) },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryBadge(job.category)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    job.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "${job.id} \u00B7 ${job.streetName} \u00B7 " +
                        (job.preferredDate ?: "no date") +
                        (job.timeSlot?.let { ", $it" } ?: ""),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.width(10.dp))
            StatusPill(job.status)
        }
    }
}
