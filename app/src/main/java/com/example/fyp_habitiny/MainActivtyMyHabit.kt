package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.fyp_habitiny.Model.ArchiveHabit
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar


class MainActivtyMyHabit : AppCompatActivity() {

    private lateinit var adapter: HabitAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_list)


        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewMyHabit)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this@MainActivtyMyHabit, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_dashboard -> {
                        val intent = Intent(this@MainActivtyMyHabit, MainActivityAddNewHabit::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this@MainActivtyMyHabit, MainActivityMotoUserInput::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_streak -> {
                        val intent = Intent(this@MainActivtyMyHabit, MainActivityStreakCounter::class.java)
                        startActivity(intent)
                    }
                }
                return true // True if the event was handled, false otherwise.
            }
        })

        //

        val dbHelper = DataBaseHelper(this)

        // Fetch the current user's ID
        val currentUserId = dbHelper.getCurrentUserId(this)

        // Fetch habits for the current user
        val habitList = if (currentUserId != -1) {
            dbHelper.getHabit(currentUserId)
        } else {
            listOf() // Return an empty list if no user is found
        }

        //Search functionality 2

        adapter = HabitAdapter(this, R.layout.activity_single_habit,
            habitList.toMutableList() , dbHelper)
        val listView: ListView = findViewById(R.id.HabitlistView)
        listView.adapter = adapter

        setupSearchView()

        //
        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    updateListView(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    updateListView(it)
                }
                return true
            }
        })
        //
        val filterButton: ImageButton = findViewById(R.id.btnFilter)
        filterButton.setOnClickListener { view ->
            showFilterPopupMenu(view)
        }

//Add habit to archive
        adapter.setOnAddHabitToArchiveListener { addedHabit ->
            val archivedHabit = ArchiveHabit(
                archivedHabitId = -1,
                archivedHabitUserId = addedHabit.HabitUserId,
                AhabitId = addedHabit.habitId,
                archivedHabitName = addedHabit.habitName,
                archivedHabitStartDate = addedHabit.habitStartDate,
                archivedHabittarget = addedHabit.habittarget,
                archivedHabittimePreference = addedHabit.habittimePreference,
                archivedHabitcurrentCount = addedHabit.habitcurrentCount,
                archivedEndDate = addedHabit.habitEndtDate,
            )

            dbHelper.addHabitToArchive(archivedHabit)

            // Fetch and update the habit list after adding a habit to archive



            val updatedHabitList = dbHelper.getHabit(currentUserId)
            adapter.clear()
            adapter.addAll(updatedHabitList)
            adapter.notifyDataSetChanged()
            listView.invalidateViews()
            listView.requestLayout()
        }
        //calender
        val calendarButton: ImageButton = findViewById(R.id.calendarView)

        calendarButton.setOnClickListener {
            showDatePicker()
        }


    }
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Log.d("MainActivity", "Selected date: $selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save ListView state
        val listView: ListView = findViewById(R.id.HabitlistView)
        outState.putInt("listViewScrollPosition", listView.firstVisiblePosition)
        // Additional state saving...
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore ListView state
        val listViewScrollPosition = savedInstanceState.getInt("listViewScrollPosition", 0)
        val listView: ListView = findViewById(R.id.HabitlistView)
        listView.setSelection(listViewScrollPosition)

    }

    fun setupSearchView() {
        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { updateListView(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { updateListView(it) }
                return true
            }
        })
    }

     fun updateListView(query: String) {
        val dbHelper = DataBaseHelper(this)
        val filteredHabits = dbHelper.searchHabits(query)
        // Log the size of filtered habits for debugging
        Log.d("MainActivtyMyHabit", "Filtered habits size: ${filteredHabits.size}")
        // Update the adapter data
        adapter.updateData(filteredHabits)
    }

    fun showFilterPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.filter_option_menu) // Make sure to define this menu in your resources
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.option1 -> {

                        val intent =Intent(this, MainActivtyMyHabit::class.java)
                        startActivity(intent)

                    true
                }
                R.id.option2 -> {
                    val intent =Intent(this, MainActivityDoneHabit::class.java)
                    startActivity(intent)
                    true
                }
                // Handle other options...
                else -> false
            }
        }
        popupMenu.show()
    }

    fun addHabitButton(view: View) {
        val intent = Intent(this, MainActivityAddNewHabit::class.java)
        startActivity(intent)
    }
    //notification
    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(habitId: Int, habitName: String, reminderDate: String) {
        val intent = Intent(this, ReminderBroadcastReceiver::class.java).apply {
            putExtra("habitId", habitId)
            putExtra("habitName", habitName)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, habitId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val reminderDateTime = LocalDate.parse(reminderDate).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderDateTime, pendingIntent)
    }
    //testest
     fun triggerTestNotification(view: View?) {
        val scheduler = NotificationScheduler(applicationContext)
        // Manually set a habit ID, name, and a reminder date for testing
        val testHabitId = 999 // Example ID
        val testHabitName = "Test Habit"
        val testReminderDate =
            LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) // Today's date for the test
        scheduler.scheduleNotification(
            applicationContext,
            testHabitId,
            testHabitName,
            testReminderDate
        )
    }
}