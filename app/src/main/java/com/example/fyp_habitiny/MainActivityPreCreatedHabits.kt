package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper

class MainActivityPreCreatedHabits : AppCompatActivity(), RecoHabitAdapter.RecoHabitClickListener {
     @SuppressLint("MissingInflatedId")
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_pre_created_habits)


         val dbHelper = DataBaseHelper(this)
         val recoHabitList = dbHelper.getPreCraetedHabits()



         val listView: ListView = findViewById(R.id.listViewPreCraetedHabit)

         val recoHabitAdapter = RecoHabitAdapter(this, R.layout.activity_single_recohabit, recoHabitList, this)
         listView.adapter = recoHabitAdapter

    }
    //creating an override fun to use the clikc listener set in the RecoHabit Adpater
    // and using intent to spacifiy the targeted activity, here we are passing data using intent
    override fun onAddRecoHabitClicked(recoHabitName: String) {
        val intent = Intent(this, MainActivityAddNewHabit::class.java)
        intent.putExtra("recoHabitName", recoHabitName)
        startActivity(intent)
    }


}