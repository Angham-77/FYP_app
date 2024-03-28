package com.example.fyp_habitiny

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper

class MainActivityDoneHabit: AppCompatActivity() {

    private lateinit var archivedHabitAdapter: ArchivedHabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done_habit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dbHelper = DataBaseHelper(this)

        // Fetch the current user's ID
        val currentUserId = dbHelper.getCurrentUserId(this)

        // Fetch archived habits for the current user
        val archivedHabitList = if (currentUserId != -1) {
            dbHelper.getArchivedHabitByUserId(currentUserId)
        } else {
            listOf() // Return an empty list if no user is found
        }
        val archivedHabitListView: ListView = findViewById(R.id.DoneHabitlistView)
        archivedHabitAdapter = ArchivedHabitAdapter(this, R.layout.activity_sinlge_done_habit, archivedHabitList, dbHelper)
        archivedHabitListView.adapter = archivedHabitAdapter

      /*  val archivedhabitList = dbHelper.getAllArchivedHabits().toMutableList()

        val archivedhabitListView: ListView = findViewById(R.id.DoneHabitlistView)
        archivedHabitAdapter = ArchivedHabitAdapter(this, R.layout.activity_sinlge_done_habit, archivedhabitList, dbHelper)
        archivedhabitListView.adapter = archivedHabitAdapter*/
    }
}