package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivityAdminOptions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_options)
    }

    fun goToAdminFeedback(view: View){
        val intent = Intent(this, MainActivityAdminFeedback::class.java)
        startActivity(intent)
    }
}