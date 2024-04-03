package com.example.fyp_habitiny


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Moto
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivtyMotoSpace : AppCompatActivity() {

    private lateinit var motoList: List<Moto>
    private var currentIndex = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moto_space)

        val dbHelper = DataBaseHelper(this)
        motoList = dbHelper.getMototext() // Load the moto texts from the database

        // Initial display
        updateMotoText()

        // Next button setup
        findViewById<ImageButton>(R.id.buttonNext).setOnClickListener {
            if (currentIndex < motoList.size - 1) {
                currentIndex++
                updateMotoText()
            }
        }

        // Prev button setup
        findViewById<ImageButton>(R.id.buttonPrev).setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateMotoText()
            }
        }


        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_view2)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this@MainActivtyMotoSpace, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_dashboard -> {
                        val intent = Intent(this@MainActivtyMotoSpace, MainActivtyMyHabit::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this@MainActivtyMotoSpace, MainActivtyMotoSpace::class.java)
                        startActivity(intent)
                    }
                }
                return true // True if the event was handled, false otherwise.
            }
        })

        //
    }

    private fun updateMotoText() {
        if (motoList.isNotEmpty()) {
            val motoTextDisplay = findViewById<TextView>(R.id.MotoTextView)
            motoTextDisplay.text = motoList[currentIndex].motoText
        }
    }
}
