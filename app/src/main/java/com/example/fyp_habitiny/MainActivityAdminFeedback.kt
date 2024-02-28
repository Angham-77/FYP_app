package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Feedback
import com.example.fyp_habitiny.Model.Moto

class MainActivityAdminFeedback : AppCompatActivity() {

    private lateinit var FeedbackList: List<Feedback>
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_admin)

        val dbHelper = DataBaseHelper(this)
        FeedbackList = dbHelper.getFeedback() // Load the moto texts from the database

        // Initial display
        updateFeedbackText()

        // Next button setup
        findViewById<ImageButton>(R.id.buttonNextAdminfeedback).setOnClickListener {
            if (currentIndex < FeedbackList.size - 1) {
                currentIndex++
                updateFeedbackText()
            }
        }

        // Prev button setup
        findViewById<ImageButton>(R.id.buttonPrevAdminfeedback).setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateFeedbackText()
            }
        }
    }

    private fun updateFeedbackText() {
        if (FeedbackList.isNotEmpty()) {
            val feedbackTextDisplay = findViewById<TextView>(R.id.AdminFeedbackTextView)
            feedbackTextDisplay.text = FeedbackList[currentIndex].feedbackText

            val ratingBar = findViewById<RatingBar>(R.id.ratingBarRetrived)
            ratingBar.rating = FeedbackList[currentIndex].feedbackRating.toFloat()
        }
    }
}