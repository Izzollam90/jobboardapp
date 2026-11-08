package com.jobboard.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Carpenter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jobboard.app.data.JobPriority
import com.jobboard.app.data.JobStatus

fun categoryIcon(category: String): ImageVector = when (category) {
    "Plumbing" -> Icons.Filled.Plumbing
    "Electrical" -> Icons.Filled.Bolt
    "HVAC" -> Icons.Filled.AcUnit
    "Carpentry" -> Icons.Filled.Carpenter
    "Appliance Repair" -> Icons.Filled.Kitchen
    "Painting" -> Icons.Filled.Brush
    "Exterior" -> Icons.Filled.Home
    else -> Icons.Filled.Handyman
}

@Composable
fun Eyebrow(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 2.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

@Composable
fun Pill(
    label: String,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(1.dp, color, RoundedCornerShape(99.dp))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
fun PriorityPill(priority: JobPriority, modifier: Modifier = Modifier) {
    val color = when (priority) {
        JobPriority.Emergency -> Color(0xFFE05B5B)
        JobPriority.Soon -> Color(0xFFE6A23C)
        JobPriority.Routine -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Pill(priority.label, color, modifier)
}

@Composable
fun StatusPill(status: JobStatus, modifier: Modifier = Modifier) {
    val color = when (status) {
        JobStatus.Completed -> Color(0xFF4CAF7D)
        JobStatus.InProgress, JobStatus.Scheduled, JobStatus.Accepted -> MaterialTheme.colorScheme.primary
        JobStatus.OnHold -> Color(0xFFE6A23C)
        JobStatus.Canceled -> Color(0xFFE05B5B)
        JobStatus.Requested -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Pill(status.label, color, modifier)
}

@Composable
fun CategoryBadge(category: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f))
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = categoryIcon(category),
            contentDescription = category,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
fun StatusTracker(status: JobStatus, modifier: Modifier = Modifier) {
    val steps = listOf(
        JobStatus.Requested, JobStatus.Accepted, JobStatus.Scheduled,
        JobStatus.InProgress, JobStatus.Completed
    )
    val current = status.step
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        steps.forEachIndexed { index, step ->
            val done = current > index
            val now = current == index
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                done -> MaterialTheme.colorScheme.primary
                                now -> MaterialTheme.colorScheme.background
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                        .border(
                            2.dp,
                            when {
                                done -> MaterialTheme.colorScheme.primary
                                now -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.outline
                            },
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (done) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    text = step.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (now || done) MaterialTheme.colorScheme.onSurface
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if (now) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
