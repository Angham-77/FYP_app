package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivityAdminRecoHabitEdit:  AppCompatActivity() {

    private lateinit var adapter: RecoHabitAdapterAdmin

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reco_habit_list_admin)


        val dbHelper = DataBaseHelper(this)
        val recoHabitList = dbHelper.getPreCraetedHabits()


        val listView: ListView = findViewById(R.id.listViewPreCraetedHabitAdmin)


        //add toMutableList to the list
        val recoHabitAdapterAdmin = RecoHabitAdapterAdmin(this, R.layout.activity_single_reco_habit_admin, recoHabitList.toMutableList())
        listView.adapter = recoHabitAdapterAdmin
        this.adapter = recoHabitAdapterAdmin
        setupSearchView()


        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewEditPreHabitAdmin)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.Home -> {
                        val intent = Intent(this@MainActivityAdminRecoHabitEdit, MainActivityAdminOptions::class.java)
                        startActivity(intent)
                    }
                    R.id.AddUser -> {
                        val intent = Intent(this@MainActivityAdminRecoHabitEdit, MainActivityAddNewUserByAdmin::class.java)
                        startActivity(intent)
                    }
                    R.id.AddHabit -> {
                        val intent = Intent(this@MainActivityAdminRecoHabitEdit, MainActivityAdminAddRecoHabit::class.java)
                        startActivity(intent)
                    }
                }
                return true // True if the event was handled, false otherwise.
            }
        })

        //

    }
    fun addRecoHabitButton(view: View) {
        val intent = Intent(this, MainActivityAdminAddRecoHabit::class.java)
        startActivity(intent)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val listView: ListView = findViewById(R.id.listViewPreCraetedHabitAdmin)
        outState.putInt("listViewScrollPosition", listView.firstVisiblePosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val listViewScrollPosition = savedInstanceState.getInt("listViewScrollPosition", 0)
        val listView: ListView = findViewById(R.id.listViewPreCraetedHabitAdmin)
        listView.setSelection(listViewScrollPosition)
    }

    fun setupSearchView() {//here con
        val searchView: SearchView = findViewById(R.id.searchViewRecoHabitAdmin)
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
        val filteredHabits = dbHelper.searchRecoHabitsAdmin(query)
        Log.d("MainActivtyRecoHabit", "Filtered habits size: ${filteredHabits.size}")
        adapter.updateData(filteredHabits) // Ensure your adapter has an `updateData` method to handle this
    }



}
