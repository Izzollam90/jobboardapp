package com.jobboard.app.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object SampleData {

    private val fmt: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    private fun day(offset: Long): String = LocalDate.now().plusDays(offset).format(fmt)

    fun jobs(): List<Job> = listOf(
        Job(
            id = "J-1041", title = "Kitchen sink leaking under cabinet",
            description = "Slow drip from the trap. A bucket is under it for now.",
            category = "Plumbing", priority = JobPriority.Soon, status = JobStatus.InProgress,
            streetName = "Maple Ridge Rd", fullAddress = "410 Maple Ridge Rd, Apt 204, Lancaster, PA 17601",
            preferredDate = day(0), timeSlot = "10 am-12 pm", claimed = true
        ),
        Job(
            id = "J-1042", title = "Hallway light fixture out",
            description = "Fixture by the stairwell flickers then goes dark.",
            category = "Electrical", priority = JobPriority.Routine, status = JobStatus.Scheduled,
            streetName = "Maple Ridge Rd", fullAddress = "410 Maple Ridge Rd, Apt 204, Lancaster, PA 17601",
            preferredDate = day(2), timeSlot = "8-10 am", claimed = true
        ),
        Job(
            id = "J-1043", title = "Gutter cleanout, both sides",
            description = "Seasonal cleanout before winter.",
            category = "Exterior", priority = JobPriority.Routine, status = JobStatus.Requested,
            streetName = "Cedar St", fullAddress = "18 Cedar St, Unit B, Lancaster, PA 17602",
            preferredDate = day(5), timeSlot = "1-3 pm", claimed = false
        ),
        Job(
            id = "J-1044", title = "Furnace filter and inspection",
            description = "Annual service. Two dogs in the home.",
            category = "HVAC", priority = JobPriority.Soon, status = JobStatus.Requested,
            streetName = "Cedar St", fullAddress = "18 Cedar St, Unit B, Lancaster, PA 17602",
            preferredDate = day(3), timeSlot = "8-10 am", claimed = false
        ),
        Job(
            id = "J-1045", title = "Unit turnover: paint and patch",
            description = "2BR turnover, move-in in 10 days.",
            category = "Painting", priority = JobPriority.Routine, status = JobStatus.Accepted,
            streetName = "Orchard Ln", fullAddress = "22 Orchard Ln, Lancaster, PA 17603",
            preferredDate = null, timeSlot = null, claimed = true
        ),
        Job(
            id = "J-1046", title = "Repaint living room and trim",
            description = "Two walls plus baseboards. Match the existing warm white.",
            category = "Painting", priority = JobPriority.Routine, status = JobStatus.Requested,
            streetName = "Maple Ridge Rd", fullAddress = "410 Maple Ridge Rd, Apt 204, Lancaster, PA 17601",
            preferredDate = day(6), timeSlot = "10 am-12 pm", claimed = false
        ),
        Job(
            id = "J-1047", title = "Dishwasher not draining",
            description = "Standing water after every cycle.",
            category = "Appliance Repair", priority = JobPriority.Emergency, status = JobStatus.Requested,
            streetName = "King St", fullAddress = "77 King St, Lancaster, PA 17602",
            preferredDate = day(1), timeSlot = "8-10 am", claimed = false
        ),
        Job(
            id = "J-1039", title = "Back door sticks",
            description = "Swelling at the latch side.",
            category = "Carpentry", priority = JobPriority.Routine, status = JobStatus.Scheduled,
            streetName = "King St", fullAddress = "77 King St, Lancaster, PA 17602",
            preferredDate = day(3), timeSlot = "1-3 pm", claimed = true
        ),
        Job(
            id = "J-1040", title = "Porch railing re-anchor",
            description = "Waiting on brackets (backordered).",
            category = "Carpentry", priority = JobPriority.Emergency, status = JobStatus.OnHold,
            streetName = "Cedar St", fullAddress = "18 Cedar St, Unit B, Lancaster, PA 17602",
            preferredDate = day(4), timeSlot = "10 am-12 pm", claimed = true
        ),
        Job(
            id = "J-1038", title = "Patch and paint drywall",
            description = "Two holes from move-out.",
            category = "Painting", priority = JobPriority.Routine, status = JobStatus.Completed,
            streetName = "Maple Ridge Rd", fullAddress = "410 Maple Ridge Rd, Apt 204, Lancaster, PA 17601",
            preferredDate = day(-9), timeSlot = "1-3 pm", claimed = true
        )
    )
}
