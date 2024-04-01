package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper

class MainActivityAdminRecoHabitEdit:  AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reco_habit_list_admin)


        val dbHelper = DataBaseHelper(this)
        val recoHabitList = dbHelper.getPreCraetedHabits()


        val listView: ListView = findViewById(R.id.listViewPreCraetedHabitAdmin)

        val recoHabitAdapter = RecoHabitAdapterAdmin(this, R.layout.activity_single_reco_habit_admin, recoHabitList)
        listView.adapter = recoHabitAdapter

    }

}
