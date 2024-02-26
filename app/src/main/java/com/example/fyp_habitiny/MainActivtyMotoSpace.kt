package com.example.fyp_habitiny


import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Moto

class MainActivtyMotoSpace : AppCompatActivity() {

    private lateinit var motoList: List<Moto>
    private var currentIndex = 0

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
    }

    private fun updateMotoText() {
        if (motoList.isNotEmpty()) {
            val motoTextDisplay = findViewById<TextView>(R.id.MotoTextView)
            motoTextDisplay.text = motoList[currentIndex].motoText
        }
    }
}
