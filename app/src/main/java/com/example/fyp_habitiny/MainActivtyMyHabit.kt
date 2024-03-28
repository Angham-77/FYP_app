package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.ArchiveHabit

import com.example.fyp_habitiny.Model.DataBaseHelper

class MainActivtyMyHabit : AppCompatActivity() {

    private lateinit var adapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_list)

        val dbHelper = DataBaseHelper(this)

        // Fetch the current user's ID
        val currentUserId = dbHelper.getCurrentUserId(this)

        // Fetch habits for the current user
        val habitList = if (currentUserId != -1) {
            dbHelper.getHabit(currentUserId)
        } else {
            listOf() // Return an empty list if no user is found
        }

        val listView: ListView = findViewById(R.id.HabitlistView)
        val searchView = findViewById<SearchView>(R.id.searchView)
        //
        val filterButton: ImageButton = findViewById(R.id.btnFilter)
        filterButton.setOnClickListener { view ->
            showFilterPopupMenu(view)
        }


        // Initialize the adapter with the habit list for the current user
        adapter = HabitAdapter(this, R.layout.activity_single_habit, habitList, dbHelper)
        listView.adapter = adapter

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
            )

            dbHelper.addHabitToArchive(archivedHabit)

            // Fetch and update the habit list after adding a habit to archive
            val updatedHabitList = dbHelper.getHabit(currentUserId)
            adapter.clear()
            adapter.addAll(updatedHabitList)
            adapter.notifyDataSetChanged()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean = true
        })
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
}