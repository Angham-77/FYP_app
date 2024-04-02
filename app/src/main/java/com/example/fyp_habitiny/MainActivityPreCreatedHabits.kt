package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.fyp_habitiny.Model.DataBaseHelper

class MainActivityPreCreatedHabits : AppCompatActivity(), RecoHabitAdapter.RecoHabitClickListener {

    private lateinit var adapter: RecoHabitAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_pre_created_habits)

        val dbHelper = DataBaseHelper(this)
        val recoHabitList = dbHelper.getPreCraetedHabits()

        val listView: ListView = findViewById(R.id.listViewPreCraetedHabit)

        //add toMutableList to the list
        val recoHabitAdapter = RecoHabitAdapter(this, R.layout.activity_single_recohabit, recoHabitList.toMutableList(), this)
        listView.adapter = recoHabitAdapter
        this.adapter = recoHabitAdapter
        setupSearchView()
    }

    // Moved inside the class
    override fun onAddRecoHabitClicked(recoHabitName: String) {
        val intent = Intent(this, MainActivityAddNewHabit::class.java)
        intent.putExtra("recoHabitName", recoHabitName)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val listView: ListView = findViewById(R.id.listViewPreCraetedHabit)
        outState.putInt("listViewScrollPosition", listView.firstVisiblePosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val listViewScrollPosition = savedInstanceState.getInt("listViewScrollPosition", 0)
        val listView: ListView = findViewById(R.id.listViewPreCraetedHabit)
        listView.setSelection(listViewScrollPosition)
    }

    fun setupSearchView() {
        val searchView: SearchView = findViewById(R.id.searchViewRecoHabit)
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
        val filteredHabits = dbHelper.searchRecoHabits(query)
        Log.d("MainActivtyRecoHabit", "Filtered habits size: ${filteredHabits.size}")
        adapter.updateData(filteredHabits) // Ensure your adapter has an `updateData` method to handle this
    }

}
