package com.example.fyp_habitiny

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Habit
import java.util.Calendar

class MainActivityAddNewHabit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        //adding th
        val habitName = intent.getStringExtra("recoHabitName")
        val editTextHabitName: EditText = findViewById(R.id.editTextHabitName)
        editTextHabitName.setText(habitName)
        //

        val editTextStartDate = findViewById<EditText>(R.id.editTextHabitStartDate)
        editTextStartDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {

            val datePickerDialog = DatePickerDialog(this, { view, year, month, dayOfMonth ->
                // Note: Month is zero-based, add 1 for display
                val selectedDate = "${dayOfMonth}/${month + 1}/${year}"
                findViewById<EditText>(R.id.editTextHabitStartDate).setText(selectedDate)
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()

    }

    fun saveNewHabitButton(view: View) {
        val habitName = findViewById<EditText>(R.id.editTextHabitName).text.toString()
        val habitStartDate = findViewById<EditText>(R.id.editTextHabitStartDate).text.toString()
        val habitTargetText = findViewById<EditText>(R.id.editTextTarget).text.toString()
        var habitPref = ""
        val message = findViewById<TextView>(R.id.textViewMessageHabit)

        // Fetch the current user ID from shared preferences
        val myDataBase = DataBaseHelper(this)
        val currentUserId = myDataBase.getCurrentUserId(this)

        val habitTarget: Int = habitTargetText.toIntOrNull() ?: 0 // Use safe call with elvis operator for default value

        val rg = findViewById<RadioGroup>(R.id.radioGroupHabitPreference)
        val rb = findViewById<RadioButton>(rg.checkedRadioButtonId)
        habitPref = when (rb.text.toString()) {
            "All Day" -> "All Day"
            "Morning" -> "Morning"
            else -> "Evening"
        }

        val validator = UserInputValidator()
        val validationResult = validator.validateHabitInput(habitName, habitStartDate)

        if (validationResult != "Valid") {
            message.text = validationResult
        } else {
            // Create the new habit with the current user's ID
            val newHabit = Habit(-1, currentUserId, habitName, habitStartDate, 0, habitTarget, habitPref, 0)
            val result = myDataBase.addHabit(newHabit)

            when (result) {
                -1 -> message.text = "Error on creating new habit"
                -2 -> message.text = "Error can not open/create database"
                else -> {
                    message.text = "Your habit has been added to the database successfully."
                    findViewById<Button>(R.id.buttonSaveHabit).isEnabled = false
                    val intent = Intent(this, MainActivtyMyHabit::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    fun viewHabitButton(view: View) {
        val intent = Intent(this, MainActivtyMyHabit::class.java)
        startActivity(intent)
    }
}