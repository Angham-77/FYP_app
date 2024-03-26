package com.example.fyp_habitiny

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper

class MainActivityDoneHabit: AppCompatActivity() {

    private lateinit var archivedHabitAdapter: ArchivedHabitAdapter
    private lateinit var dbHelper: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done_habit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DataBaseHelper(this)
        val archivedhabitList = dbHelper.getAllArchivedHabits().toMutableList()

        val archivedhabitListView: ListView = findViewById(R.id.DoneHabitlistView)
        archivedHabitAdapter = ArchivedHabitAdapter(this, R.layout.activity_sinlge_done_habit, archivedhabitList, dbHelper)
        archivedhabitListView.adapter = archivedHabitAdapter
    }
}