package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Feedback
import com.example.fyp_habitiny.Model.Moto
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivityMotoUserInput: AppCompatActivity() {

    private val userInputValidator = UserInputValidator()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moto_input)
        //


        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewAddMoto)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this@MainActivityMotoUserInput, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_dashboard -> {
                        val intent = Intent(this@MainActivityMotoUserInput, MainActivityAddNewHabit::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this@MainActivityMotoUserInput, MainActivityMotoUserInput::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_streak -> {
                        val intent = Intent(this@MainActivityMotoUserInput, MainActivityStreakCounter::class.java)
                        startActivity(intent)
                    }
                }
                return true // True if the event was handled, false otherwise.
            }
        })

        //
    }

    fun saveNewMotoButton(view: View) {

        val MotoText = findViewById<EditText>(R.id.editTextEntreMoto).text.toString()
        val message = findViewById<TextView>(R.id.textViewMotoInputMessage)



        if(MotoText.isEmpty() )

            message.text = "Entry is required!"
        else if(MotoText.isEmpty())

            message.text = "Feedback Content is required!"

        // Call the validation function
        val validationMessage = userInputValidator.validateMotoInput(MotoText)

        if (validationMessage != "Valid") {
            // If validation fails, show the error message and stop further execution
            message.text = validationMessage
            return
        }
        else { // Save data
            val mydatabase = DataBaseHelper(this)
            val currentUserId = mydatabase.getCurrentUserId(this)
            val newMoto = Moto(-1, MotoText, currentUserId)
            //save data

            val result = mydatabase.addMoto(newMoto)


            when(result) {

                -1 -> message.text = "Error on creating new habit"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "Moto is already exist"
                else ->  {
                    message.text = "Thank you! Your Entry has been submitted successfully "
                    findViewById<Button>(R.id.buttonSubmitMoto).isEnabled = false
               //     val intent = Intent(this, MainActivtyReadyBtn::class.java)
                   // startActivity(intent)
                }
            }
        }
    }
    fun GoToMotoSpaceFromEdit(view: View) {
        val intent = Intent(this, MainActivtyMotoSpace::class.java)
        startActivity(intent)
    }
}