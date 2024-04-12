package com.example.fyp_habitiny

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Habit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.util.Calendar

class MainActivityAddNewHabit : AppCompatActivity() {

    private val userInputValidator = UserInputValidator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewAddHabit)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this@MainActivityAddNewHabit, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_dashboard -> {
                        val intent = Intent(this@MainActivityAddNewHabit, MainActivityAddNewHabit::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this@MainActivityAddNewHabit, MainActivityMotoUserInput::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_streak -> {
                        val intent = Intent(this@MainActivityAddNewHabit, MainActivityStreakCounter::class.java)
                        startActivity(intent)
                    }
                }
                return true // True if the event was handled, false otherwise.
            }
        })

        //

        val editTextHabitName: EditText = findViewById(R.id.editTextHabitName)
        val editTextStartDate = findViewById<EditText>(R.id.editTextHabitStartDate)
        val editTextEndDate = findViewById<EditText>(R.id.editTextHabitEndDate)

        //4 Retrieve habit name and target from intent if available (for reactivating a habit)
        intent.getStringExtra("EXTRA_HABIT_NAME")?.let { habitName ->
            editTextHabitName.setText(habitName)
        }
        val habitTarget = intent.getIntExtra("EXTRA_HABIT_TARGET", 0)
        if (habitTarget > 0) {
            findViewById<EditText>(R.id.editTextTarget).setText(habitTarget.toString())
        }

        editTextStartDate.setOnClickListener {
            showDatePickerDialog()
        }

        editTextEndDate.setOnClickListener {
            showDatePickerDialogEnd()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            // Note: Month is zero-based, add 1 for display
            val selectedDate = "${dayOfMonth}/${month + 1}/${year}"
            findViewById<EditText>(R.id.editTextHabitStartDate).setText(selectedDate)
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()
    }
    private fun showDatePickerDialogEnd() {
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            // Note: Month is zero-based, add 1 for display
            val selectedDate = "${dayOfMonth}/${month + 1}/${year}"
            findViewById<EditText>(R.id.editTextHabitEndDate).setText(selectedDate)
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()
    }

    fun saveNewHabitButton(view: View) {
        val habitName = findViewById<EditText>(R.id.editTextHabitName).text.toString()
        val habitStartDate = findViewById<EditText>(R.id.editTextHabitStartDate).text.toString()
        val habitEndDate = findViewById<EditText>(R.id.editTextHabitEndDate).text.toString()
        val habitTargetText = findViewById<EditText>(R.id.editTextTarget).text.toString()
        val message = findViewById<TextView>(R.id.textViewMessageHabit)

        // Fetch the current user ID from shared preferences
        val myDataBase = DataBaseHelper(this)
        val currentUserId = myDataBase.getCurrentUserId(this)

        val habitTarget: Int = habitTargetText.toIntOrNull() ?: 0 // Use safe call with elvis operator for default value
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupHabitPreference)
        val habitPref: String = if (radioGroup.checkedRadioButtonId != -1) {
            findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
        } else {
            "" // or any default value you find appropriate
        }

        if (habitName.isBlank() || habitStartDate.isBlank() || habitPref.isBlank()) {
            message.text = "Please fill all the fields."
            return
        }
        // Call the validation function
        val validationMessage = userInputValidator.validateHabitInput(habitName, habitStartDate, habitEndDate, habitTarget)

        if (validationMessage != "Valid") {
            // If validation fails, show the error message and stop further execution
            message.text = validationMessage
            return
        }

        // Create the new habit with the current user's ID
        val newHabit = Habit(-1, currentUserId, habitName, habitStartDate, 0, habitTarget, habitPref, 0, habitEndDate)
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
    fun goTStreak(view: View) {
        val intent = Intent(this, MainActivityStreakCounter::class.java)
        startActivity(intent)
    }
}
