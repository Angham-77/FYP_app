package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Habit
import com.example.fyp_habitiny.Model.RecoHabit

class MainActivityAdminAddRecoHabit: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_reco_habit)
    }
    fun saveNewRecoHabitButton(view: View) {
        val habitName = findViewById<EditText>(R.id.editTextRecoHabitName).text.toString()
        val message = findViewById<TextView>(R.id.textViewMessageRecoHabit)

        // Fetch the current user ID from shared preferences
        val myDataBase = DataBaseHelper(this)
        val currentUserId = myDataBase.getCurrentAdminUserId(this)

        // Create the new habit with the current user's ID
        val newHabit = RecoHabit(-1, habitName, currentUserId)
        val result = myDataBase.addRecoHabit(newHabit)

        when (result) {
            -1 -> message.text = "Error on creating new habit"
            -2 -> message.text = "Error can not open/create database"
            -3 -> message.text = "This habit already exists." // Handle habit already exists
            else -> {
                message.text = "Your habit has been added to the database successfully."
                findViewById<Button>(R.id.buttonSaveRecoHabit).isEnabled = false
              //  val intent = Intent(this, MainActivityAdminRecoHabitEdit::class.java)
              //  startActivity(intent)
            }
        }
    }

    fun viewHabitButton(view: View) {
        val intent = Intent(this, MainActivityAdminRecoHabitEdit::class.java)
        startActivity(intent)
    }


}