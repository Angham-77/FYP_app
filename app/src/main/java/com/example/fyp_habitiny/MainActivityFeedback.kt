package com.example.fyp_habitiny

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivityFeedback : AppCompatActivity() {

    private val userInputValidator = UserInputValidator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provide_feedback)


        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewFeedback)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this@MainActivityFeedback, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_dashboard -> {
                        val intent = Intent(this@MainActivityFeedback, MainActivityAddNewHabit::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this@MainActivityFeedback, MainActivityMotoUserInput::class.java)
                        startActivity(intent)
                    }
                }
                return true // True if the event was handled, false otherwise.
            }
        })

        //
    }
    fun saveNewFeedbackButton(view: View) {

        val FeedbackText = findViewById<EditText>(R.id.editTextFeedback).text.toString()
        val ratingBar = findViewById<RatingBar>(R.id.ratingBarFeedback)
        val Rating = ratingBar.rating.toDouble() // Assuming rating is a Double property in Feedback
        val message = findViewById<TextView>(R.id.textViewMessageFeedback)



        if(FeedbackText.isEmpty() ) // First and last name are required
        // Toast.makeText(this,"First name and last name are required!",Toast.LENGTH_LONG).show()
            message.text = "Feedback is required!"
        else if(FeedbackText.isEmpty()) // // User name and password are required
        //  Toast.makeText(this,"User name and Password are required!",Toast.LENGTH_LONG).show()
            message.text = "Feedback Content is required!"

        // Call the validation function
        val validationMessage = userInputValidator.validateFeedbackInput(FeedbackText)

        if (validationMessage != "Valid") {
            // If validation fails, show the error message and stop further execution
            message.text = validationMessage
            return
        }
        else { // Save data

            val newFeedback = Feedback(-1, FeedbackText, Rating)
            //save data
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addFeedback(newFeedback)

            when(result) {

                -1 -> message.text = "Error on creating new habit"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "User name is already exist"
                else ->  {
                    message.text = "Thank you! Your Feedback has been submitted successfully "
                    findViewById<Button>(R.id.buttonSubmitFeedback).isEnabled = false
                    val intent = Intent(this, MainActivtyReadyBtn::class.java)
                    startActivity(intent)
                }
            }
        }
    }

}