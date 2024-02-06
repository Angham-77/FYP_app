package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper

class MainActivtyMyHabit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_list)


        val dbHelper = DataBaseHelper(this)
        val habitList = dbHelper.getHabit()


        val listView: ListView = findViewById(R.id.HabitlistView)

        val habitAdapter = HabitAdapter(this, R.layout.activity_single_habit, habitList)
        listView.adapter = habitAdapter


    }
    fun addHabitButton(view: View) {
        val intent = Intent(this, MainActivityAddNewHabit::class.java)
        startActivity(intent)
    }

}