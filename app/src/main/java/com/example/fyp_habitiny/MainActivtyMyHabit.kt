package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.ArchiveHabit

import com.example.fyp_habitiny.Model.DataBaseHelper

class MainActivtyMyHabit : AppCompatActivity() {

    private var addHabitToArchive = 0
    private lateinit var adapter: HabitAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_list)


        val dbHelper = DataBaseHelper(this)
        val habitList = dbHelper.getHabit()


        val listView: ListView = findViewById(R.id.HabitlistView)
        val searchView = findViewById<SearchView>(R.id.searchView)


        adapter = HabitAdapter(this, R.layout.activity_single_habit, habitList, dbHelper)
        listView.adapter = adapter

///
        adapter.setOnAddHabitToArchiveListener {addedHabit ->

            val archivedHabit = ArchiveHabit(
                archivedHabitId = -1,
                archivedHabitUserId = 0,
                AhabitId = addedHabit.habitId,
                archivedHabitName = addedHabit.habitName,
                archivedHabitStartDate = addedHabit.habitStartDate,
              //  archivedHabitStatus = addedHabit.habitStatus,
                archivedHabittarget = addedHabit.habittarget,
                archivedHabittimePreference = addedHabit.habittimePreference,
                archivedHabitcurrentCount = addedHabit.habitcurrentCount,


            )
            // Call the move habit method in DataBaseHelper
            dbHelper.addHabitToArchive(archivedHabit)
        }

        ////

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
              //  habitAdapter.filter(newText.orEmpty())
                return true
            }


        })
    }

    fun addHabitButton(view: View) {
        val intent = Intent(this, MainActivityAddNewHabit::class.java)
        startActivity(intent)
    }

}