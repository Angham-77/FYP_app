package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
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

}