package com.example.fyp_habitiny

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.User
import com.example.fyp_habitiny.Model.PasswordHasher
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. You can proceed with scheduling notifications.
                Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                // Inform the user that the permission is essential for notifications.
                Toast.makeText(this, "Notification Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    //notification
    private fun checkAndRequestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted; you can schedule a notification.
            }

            else -> {
                // You can directly ask for the permission.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //
        NotificationScheduler(this).checkHabitsForReminder()
        checkAndRequestNotificationPermission()
        createNotificationChannel() //step2

        // Test: Check for habits that are due tomorrow
        val reminderDate = LocalDate.now().plusDays(1) // You get the reminder date
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") // This is the format you want
        val formattedReminderDate = reminderDate.format(formatter) // Now you format the reminderDate

        // You should use the formattedReminderDate you just created
        val dbHelper2 = DataBaseHelper(this)
        val cursor = dbHelper2.getHabitsNearEndDate(formattedReminderDate)

        // Rest of your test code...
        // Ensure to close the cursor after you are done using it
        cursor.close()
        //


        //notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Habit Reminder Channel"
            val descriptionText = "Channel for Habit End Date Reminder"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("YOUR_CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
    //notification

    override fun onStart() {
        super.onStart()
        showNotificationEveryTime() // Step 3: Show the notification
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
   /* private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }*/

   /* private fun showNotificationEveryTime() {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure you have a valid drawable resource
            .setContentTitle("Welcome Back!")
            .setContentText("Check out your habits.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // NotificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notificationBuilder.build())
    }*/
   private fun showNotificationEveryTime() {
       val dbHelper = DataBaseHelper(this)
       val currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
       val cursor = dbHelper.getHabitsNearEndDate(currentDate)

       val habitsDueSoon = mutableListOf<String>()

       while (cursor.moveToNext()) {
           // Access the column name using the getter method
           val habitName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.habitColumnName))
           habitsDueSoon.add(habitName)
       }
       cursor.close()

       val title: String
       val text: String

       when {
           habitsDueSoon.isEmpty() -> return // No habits are due soon.
           habitsDueSoon.size == 1 -> {
               // If there's only one habit due soon.
               title = "Habit Reminder"
               text = "Your habit '${habitsDueSoon.first()}' is due soon. Keep going!"
           }
           else -> {
               // If there are multiple habits.
               title = "Habit Reminders"
               text = "You have ${habitsDueSoon.size} habits nearing their end dates. Check your app!"
           }
       }

       if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
           return
       }

       val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
           .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with a valid drawable resource for your app
           .setContentTitle(title)
           .setContentText(text)
           .setPriority(NotificationCompat.PRIORITY_DEFAULT)

       NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notificationBuilder.build())
   }


    companion object {
        private const val CHANNEL_ID = "YOUR_CHANNEL_ID"
        private const val NOTIFICATION_ID = 1
        const val Habit_Column_Name = "HabitName"
    }
        fun loginButton(view: View) {
            val message = findViewById<TextView>(R.id.textViewMessageMainActivity)
            val userName = findViewById<EditText>(R.id.editTextUserName).text.toString()
            val userPassword = findViewById<EditText>(R.id.editTextPassword).text.toString()

            if (userName.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Please insert Username and Password", Toast.LENGTH_LONG)
                    .show()
            } else {
                val myDataBase = DataBaseHelper(this)

                // Hash the entered password
                val hashedEnteredPassword = PasswordHasher.hashPassword(userPassword)
                println("Hashed password: $hashedEnteredPassword")

                // Verify the password
                val result =
                    myDataBase.getUser(User(-1, "", "", "", userName, hashedEnteredPassword))
                println("DataBase =  $result")

                when (result) {
                    -1 -> message.text = "User Not Found, Please try again"
                    -2 -> message.text = "Error Cannot Open/Create DataBase"
                    in 1..Int.MAX_VALUE -> {
                        //new
                        // Save the user ID in shared preferences as the current user ID
                        myDataBase.saveCurrentUserId(result, this)
                        //
                        message.text = "You logged in successfully"

                        // Launch the new activity
                        val intent = Intent(this, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }

                    else -> message.text = "Incorrect username or password"
                }
            }
        }

        fun registerButton(view: View) {
            // val hash = Bcrypt.hash(binding.)
            val intent = Intent(this, MainActivityNewUser::class.java)
            startActivity(intent)

        }

        fun goToAdminLogin(view: View) {
            val intent = Intent(this, MainActivityAdminLogin::class.java)
            startActivity(intent)
        }
    }
