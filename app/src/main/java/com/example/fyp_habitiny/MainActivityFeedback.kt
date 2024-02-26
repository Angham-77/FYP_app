package com.example.fyp_habitiny

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Feedback

class MainActivityFeedback : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provide_feedback)
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
        else { // Save data

            val newFeedback = Feedback(-1, 0, FeedbackText, Rating)
            //save data
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addFeedback(newFeedback)

            when(result) {

                -1 -> message.text = "Error on creating new habit"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "User name is already exist"
                else ->  {
                    message.text = "Thank you! Your Feedback has been add to the database successfully "
                    findViewById<Button>(R.id.buttonSubmitFeedback).isEnabled = false
                }
            }
        }
    }

}