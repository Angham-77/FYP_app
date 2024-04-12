package com.example.fyp_habitiny

import org.junit.Assert.*

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotificationSchedulerTest {

    // A simplified version of NotificationScheduler for testing logic that doesn't require Android context
    class NotificationSchedulerMock {
        fun shouldScheduleReminderForHabit(endDate: String): Boolean {
            val endDateParsed = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE)
            val tomorrow = LocalDate.now().plusDays(1)
            return endDateParsed.isEqual(tomorrow)
        }
    }

    @Test
    fun reminderForHabitDueTomorrow_ShouldSchedule() {
        // Arrange
        val scheduler = NotificationSchedulerMock()
        val tomorrowDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)

        // Act
        val shouldSchedule = scheduler.shouldScheduleReminderForHabit(tomorrowDate)

        // Assert
        assertTrue("Reminder for a habit due tomorrow should be scheduled", shouldSchedule)
    }

    @Test
    fun reminderForHabitDueToday_ShouldNotSchedule() {
        // Arrange
        val scheduler = NotificationSchedulerMock()
        val todayDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)

        // Act
        val shouldSchedule = scheduler.shouldScheduleReminderForHabit(todayDate)

        // Assert
        assertFalse("Reminder for a habit due today should not be scheduled", shouldSchedule)
    }

}
