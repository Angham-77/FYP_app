package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.fyp_habitiny.Model.DataBaseHelper
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NotificationScheduler (private val context: Context) {

    private val dbHelper = DataBaseHelper(context)


    //test
    // Checks if a habit's end date is exactly one day from now
    fun shouldScheduleReminderForHabit(endDate: String): Boolean {
        val endDateParsed = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE)
        val tomorrow = LocalDate.now().plusDays(1)
        return endDateParsed.isEqual(tomorrow)
    }


    //

    /*fun checkHabitsForReminder() {
        val reminderDate = LocalDate.now().plusDays(1)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedReminderDate = reminderDate.format(formatter)

        val cursor = dbHelper.getHabitsNearEndDate(formattedReminderDate)
        //
        val currentDate = LocalDate.now()
      //  val reminderDate = currentDate.plusDays(1).toString()

     //   val cursor = dbHelper.getHabitsNearEndDate(reminderDate)

        while (cursor.moveToNext()) {
            val habitId = cursor.getInt(0)
            val habitName = cursor.getString(1)
            scheduleNotification(context, habitId, habitName, formattedReminderDate)
        }
        cursor.close()
    }*/
   @SuppressLint("Range")
   fun checkHabitsForReminder() {
       // Hard-coded date known to exist in your database for testing purposes
       val hardCodedDate = "11/04/2024" // Format "dd/MM/yyyy"

       // Fetch habits with the hard-coded end date
       val cursor = dbHelper.getHabitsNearEndDate(hardCodedDate)

       // Log the count of habits fetched for verification
       Log.d("NotificationScheduler", "Number of habits fetched: ${cursor.count}")

       while (cursor.moveToNext()) {
           val habitId = cursor.getInt(cursor.getColumnIndex("HabitId"))
           val habitName = cursor.getString(cursor.getColumnIndex("HabitName"))

           // Schedule a notification for this habit (this method remains unchanged)
           scheduleNotification(context, habitId, habitName, hardCodedDate)
       }
       cursor.close()
   }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(context: Context, habitId: Int, habitName: String, reminderDate: String) {
        Log.d("NotificationScheduler", "Scheduling notification for: " + habitName + " on " + reminderDate);
        // Define multiple trigger times
        val triggerTimes = arrayOf("23:10", "12:00", "23:40")

        triggerTimes.forEach { time ->
            val (hour, minute) = time.split(":").map { it.toInt() }
            val reminderDateTime = LocalDate.parse(reminderDate)
                .atTime(hour, minute)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

            // Create a unique requestCode for each alarm to prevent overwriting
            val requestCode = habitId * 100 + hour + minute // Example formula to generate unique request codes

            val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
                putExtra("habitId", habitId)
                putExtra("habitName", habitName)
                // Consider adding the trigger time or other relevant information if needed
            }
            val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderDateTime, pendingIntent)
        }
    }


  /*  @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(context: Context, habitId: Int, habitName: String, reminderDate: String) {
        // Intent to trigger the broadcast receiver
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            putExtra("habitId", habitId)
            putExtra("habitName", habitName)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, habitId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Convert reminderDate to milliseconds
        val reminderDateTime = LocalDate.parse(reminderDate, DateTimeFormatter.ISO_LOCAL_DATE)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant().toEpochMilli()

        // Schedule the notification
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderDateTime, pendingIntent)
    }*/
}