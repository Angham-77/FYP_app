package com.example.fyp_habitiny

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.ZoneId
import com.example.fyp_habitiny.Model.DataBaseHelper

class ReminderBroadcastReceiver : BroadcastReceiver() {
  /* override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return // Early return if context is null

        val habitId = intent?.getIntExtra("habitId", 0) ?: 0
        val habitName = intent?.getStringExtra("habitName") ?: ""

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // You can decide to request for permission here or handle the lack of permission appropriately
            return
        }

        val notificationBuilder = NotificationCompat.Builder(context, "YOUR_CHANNEL_ID")
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle("Habit Reminder")
            .setContentText("Your habit '$habitName' is nearing its end date. Keep going!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(habitId, notificationBuilder.build())

    }*/
  override fun onReceive(context: Context?, intent: Intent?) {
      Log.d("ReminderBroadcastReceiver", "onReceive triggered");
      if (context == null) return // Early return if context is null

      when (intent?.action) {
          Intent.ACTION_BOOT_COMPLETED -> {
              // Logic to reschedule notifications upon device boot
              rescheduleNotifications(context)
          }
          else -> {
              // Existing logic to show notification
              val habitId = intent?.getIntExtra("habitId", 0) ?: 0
              val habitName = intent?.getStringExtra("habitName") ?: ""

              if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                  // Permission is not granted
                  return
              }

              val notificationBuilder = NotificationCompat.Builder(context, "YOUR_CHANNEL_ID") // Use your consistent channel ID
                  .setSmallIcon(R.drawable.baseline_notifications_active_24)
                  .setContentTitle("Habit Reminder")
                  .setContentText("Your habit '$habitName' is nearing its end date. Keep going!")
                  .setPriority(NotificationCompat.PRIORITY_HIGH)

              val notificationManager = NotificationManagerCompat.from(context)
              notificationManager.notify(habitId, notificationBuilder.build())
          }
      }
  }
    private fun rescheduleNotifications(context: Context) {
        val dbHelper = DataBaseHelper(context)
        val currentDate = LocalDate.now()
        val reminderDate = currentDate.plusDays(1).toString()

        // Example query that gets all reminders to be rescheduled
        // Modify this to fit your actual data and requirements
        val cursor = dbHelper.getHabitsNearEndDate(reminderDate)

        while (cursor.moveToNext()) {
            val habitId = cursor.getInt(cursor.getColumnIndexOrThrow("habitId"))
            val habitName = cursor.getString(cursor.getColumnIndexOrThrow("habitName"))
            val endDate = cursor.getString(cursor.getColumnIndexOrThrow("endDate")) // Assuming you store this

            // Here we use a simplified version of scheduleNotification for demonstration
            // You'll need to adapt this to match your actual scheduling logic,
            // especially considering any specific timing or other parameters.
            scheduleNotification(context, habitId, habitName, endDate)
        }
        cursor.close()
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(context: Context, habitId: Int, habitName: String, endDate: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            putExtra("habitId", habitId)
            putExtra("habitName", habitName)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, habitId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Schedule for the end date at a specific time, for simplicity's sake we'll use 8 AM
        val reminderDateTime = LocalDate.parse(endDate).atTime(8, 0)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderDateTime, pendingIntent)
    }

}
