package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.ArchiveHabit

class MainActivityDoneHabit: AppCompatActivity(), ArchivedHabitAdapter.OnReactivateHabitListener {

    private lateinit var archivedHabitAdapter: ArchivedHabitAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done_habit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dbHelper = DataBaseHelper(this)

        val filterButton: ImageButton = findViewById(R.id.btnFilterArchive)
        filterButton.setOnClickListener { view ->
            showFilterPopupMenu(view)
        }
        //Retriving the archived habits based on user id
        // Fetch the current user's ID
        val currentUserId = dbHelper.getCurrentUserId(this)

        // Fetch archived habits for the current user
        val archivedHabitList = if (currentUserId != -1) {
            dbHelper.getArchivedHabitByUserId(currentUserId)
        } else {
            listOf<ArchiveHabit>() // Return an empty list if no user is found
        }

        val archivedHabitListView: ListView = findViewById(R.id.DoneHabitlistView)
        archivedHabitAdapter = ArchivedHabitAdapter(this, R.layout.activity_sinlge_done_habit, archivedHabitList.toMutableList(), dbHelper)
        archivedHabitListView.adapter = archivedHabitAdapter
        archivedHabitAdapter.setOnReactivateHabitListener(this)

        //Search habit based on user habits
        // Fetch and update the habit list after adding a habit to archive
        //Search functionality 2

        archivedHabitAdapter = ArchivedHabitAdapter(this, R.layout.activity_single_habit,
            archivedHabitList.toMutableList() , dbHelper)
       // val listView: ListView = findViewById(R.id.DoneHabitlistView)
        archivedHabitListView.adapter = archivedHabitAdapter

        setupSearchView()


        val updatedHabitList = dbHelper.getArchivedHabitByUserId(currentUserId)
        archivedHabitAdapter.clear()
        archivedHabitAdapter.addAll(updatedHabitList)
        archivedHabitAdapter.notifyDataSetChanged()
        archivedHabitListView.invalidateViews()
        archivedHabitListView.requestLayout()
    }
    fun setupSearchView() {
        val searchView: SearchView = findViewById(R.id.searchViewDoneHabit)
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
    //2 after adapter implement the listener in the activity that's responsible to archive habit
    override fun onReactivateHabit(habitName: String, habitTarget: Int) {
        val intent = Intent(this, MainActivityAddNewHabit::class.java).apply {
            putExtra("EXTRA_HABIT_NAME", habitName)
            putExtra("EXTRA_HABIT_TARGET", habitTarget)
        }
        startActivity(intent)
    }
    fun showFilterPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.filter_option_menu) // Make sure to define this menu in your resources
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.option1 -> {

                    val intent = Intent(this, MainActivtyMyHabit::class.java)
                    startActivity(intent)

                    true
                }
                R.id.option2 -> {
                    val intent = Intent(this, MainActivityDoneHabit::class.java)
                    startActivity(intent)
                    true
                }
                // Handle other options...
                else -> false
            }
        }
        popupMenu.show()
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
        val listView: ListView = findViewById(R.id.DoneHabitlistView)
        listView.setSelection(listViewScrollPosition)

    }
    fun updateListView(query: String) {
        val dbHelper = DataBaseHelper(this)
        val filteredHabits = dbHelper.searchArchivedHabits(query)
        // Log the size of filtered habits for debugging
        Log.d("MainActivtyMyHabit", "Filtered habits size: ${filteredHabits.size}")
        // Update the adapter data
        archivedHabitAdapter.updateData(filteredHabits)
    }


}