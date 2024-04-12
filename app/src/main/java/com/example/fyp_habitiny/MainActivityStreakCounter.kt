package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivityStreakCounter : AppCompatActivity() {

    private lateinit var tvDailyCounter: TextView
    private lateinit var tvTotalCounter: TextView

    private var dailyCounter = 0
    private var totalDaysCounter = 0
    private lateinit var lastLoginDate: LocalDate

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streak_counter)

        tvDailyCounter = findViewById(R.id.dailyCounter)
        tvTotalCounter = findViewById(R.id.totalDaysCounter)

        loadCounters()
        saveCounters()
        checkAndIncrementCounters()
        updateCountersUI()

        //nav bottom
        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewStreak)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this@MainActivityStreakCounter, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_dashboard -> {
                        val intent = Intent(this@MainActivityStreakCounter, MainActivityAddNewHabit::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this@MainActivityStreakCounter, MainActivityMotoUserInput::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_streak -> {
                        val intent = Intent(this@MainActivityStreakCounter, MainActivityStreakCounter::class.java)
                        startActivity(intent)
                    }
                }
                return true
            }
        })

        //
    }

   /* private fun loadCounters() {
        val prefs = getSharedPreferences("LoginCounters", MODE_PRIVATE)
        dailyCounter = prefs.getInt("dailyCounter", 0)
        totalDaysCounter = prefs.getInt("totalDaysCounter", 0)
        lastLoginDate = LocalDate.parse(prefs.getString("lastLoginDate", LocalDate.now().format(DateTimeFormatter.ISO_DATE)), DateTimeFormatter.ISO_DATE)

        Log.d("LoginStreak", "Loading counters. Daily: $dailyCounter, Total: $totalDaysCounter, Last Login Date: ${lastLoginDate.format(DateTimeFormatter.ISO_DATE)}")
        updateCountersUI()
    }

    private fun saveCounters() {
        val prefs = getSharedPreferences("LoginCounters", MODE_PRIVATE).edit()
        prefs.putInt("dailyCounter", dailyCounter)
        prefs.putInt("totalDaysCounter", totalDaysCounter)
        prefs.putString("lastLoginDate", lastLoginDate.format(DateTimeFormatter.ISO_DATE))
        prefs.apply()

        Log.d("LoginStreak", "Saving counters. Daily: $dailyCounter, Total: $totalDaysCounter, Last Login Date: ${lastLoginDate.format(DateTimeFormatter.ISO_DATE)}")
    }*/
   private fun saveCounters() {
       val prefs = getSharedPreferences("LoginCounters", MODE_PRIVATE).edit()
       prefs.putInt("dailyCounter", dailyCounter)
       prefs.putInt("totalDaysCounter", totalDaysCounter)
       prefs.putString("lastLoginDate", lastLoginDate.format(DateTimeFormatter.ISO_DATE))
       prefs.apply()

       Log.d("LoginStreak", "Saving counters. Daily: $dailyCounter, Total: $totalDaysCounter")
   }

    private fun loadCounters() {
        val prefs = getSharedPreferences("LoginCounters", MODE_PRIVATE)
        dailyCounter = prefs.getInt("dailyCounter", 0)
        totalDaysCounter = prefs.getInt("totalDaysCounter", 0)
        lastLoginDate = LocalDate.parse(prefs.getString("lastLoginDate", LocalDate.now().format(DateTimeFormatter.ISO_DATE)), DateTimeFormatter.ISO_DATE)

        Log.d("LoginStreak", "Loading counters. Daily: $dailyCounter, Total: $totalDaysCounter")
    }

   /* private fun checkAndIncrementCounters() {
        val currentDate = LocalDate.now()
        if (!currentDate.isEqual(lastLoginDate)) {
            Log.d("LoginStreak", "Checking and incrementing counters. Current date: $currentDate, Last login date: $lastLoginDate")
            incrementCounters()
        } else {
            Log.d("LoginStreak", "Date is equal to last login date. Not incrementing.")
        }

        lastLoginDate = currentDate // Update last login date to today
        saveCounters() // Save updated counters and date
        updateCountersUI() // Update UI
    }*/
   private fun checkAndIncrementCounters() {
       //incrementCounters()
       val currentDate = LocalDate.now()
       if (!currentDate.isEqual(lastLoginDate)) {
           Log.d("LoginStreak", "Checking and incrementing counters. Current date: $currentDate, Last login date: $lastLoginDate")
           incrementCounters()
       } else {
           Log.d("LoginStreak", "Date is equal to last login date. Not incrementing.")
       }
       lastLoginDate = currentDate // This line updates the last login date regardless of whether the counters are incremented
       saveCounters() // Save updated counters and date
       updateCountersUI() // Update UI
   }

    private fun incrementCounters() {
        dailyCounter++
        totalDaysCounter++

        Log.d("LoginStreak", "Incrementing counters. Daily: $dailyCounter, Total: $totalDaysCounter")

        if (dailyCounter > 7) {
            dailyCounter = 1 // Reset daily counter after a week
            Toast.makeText(this, "Congratulations! 7-day streak!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCountersUI() {
        tvDailyCounter.text = "$dailyCounter"
        tvTotalCounter.text = "Total Counter: $totalDaysCounter"
    }
}
