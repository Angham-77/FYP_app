package com.example.fyp_habitiny

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

        val HabitName = findViewById<EditText>(R.id.editTextHabitName).text.toString()
        val HabitStartDate  = findViewById<EditText>(R.id.editTextHabitStartDate).text.toString()

        val message = findViewById<TextView>(R.id.textViewMessageHabit)



        if(HabitName.isEmpty() ) // First and last name are required
        // Toast.makeText(this,"First name and last name are required!",Toast.LENGTH_LONG).show()
            message.text = "Habit are required!"
        else if(HabitName.isEmpty() || HabitStartDate.isEmpty() ) // // User name and password are required
        //  Toast.makeText(this,"User name and Password are required!",Toast.LENGTH_LONG).show()
            message.text = "Habit name and Start date are required!"
        else { // Save data

            val newHabit = Habit(-1,0, HabitName, HabitStartDate, 0 )
            //save data
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addHabit(newHabit)

            when(result) {

                -1 -> message.text = "Error on creating new habit"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "User name is already exist"
                else ->  {
                    message.text = "Your habit has been add to the database successfully "
                    findViewById<Button>(R.id.buttonSaveHabit).isEnabled = false
                }
            }
        }
    }
}