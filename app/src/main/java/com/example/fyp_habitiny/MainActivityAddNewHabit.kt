package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Habit

class MainActivityAddNewHabit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)
    }

    fun saveNewHabitButton(view: View) {
        val habitName = findViewById<EditText>(R.id.editTextHabitName).text.toString()
        val habitStartDate = findViewById<EditText>(R.id.editTextHabitStartDate).text.toString()
        val message = findViewById<TextView>(R.id.textViewMessageHabit)

        val validator = UserInputValidator()
        val validationResult = validator.validateHabitInput(habitName, habitStartDate)

        if (validationResult != "Valid") {
            message.text = validationResult
        } else {
            // Proceed with saving the habit as before
            val newHabit = Habit(-1, 0, habitName, habitStartDate, 0)
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addHabit(newHabit)

            when (result) {
                -1 -> message.text = "Error on creating new habit"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "User name is already exist"
                else -> {
                    message.text = "Your habit has been add to the database successfully "
                    findViewById<Button>(R.id.buttonSaveHabit).isEnabled = false
                }
            }
        }
    }
    fun viewHabitButton(view: View) {
        val intent = Intent(this, MainActivtyMyHabit::class.java)
        startActivity(intent)
    }
}