package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivtyReadyBtn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_habitbtn)
    }

    fun readyButton(view: View) {
        val intent = Intent(this, MainActivtyMyHabit::class.java)
        startActivity(intent)
    }
    fun addHabitButton2(view: View) {
        val intent = Intent(this, MainActivityAddNewHabit::class.java)
        startActivity(intent)
    }
    fun viewRecoHabits(view: View) {
        val intent = Intent(this, MainActivityPreCreatedHabits::class.java)
        startActivity(intent)
    }
}