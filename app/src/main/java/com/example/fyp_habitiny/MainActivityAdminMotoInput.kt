package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Moto
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivityAdminMotoInput : AppCompatActivity() {

    private val userInputValidator = UserInputValidator()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_moto_input)
        //


        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewAddMotoAdmin)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.Home -> {
                        val intent = Intent(
                            this@MainActivityAdminMotoInput,
                            MainActivityAdminOptions::class.java
                        )
                        startActivity(intent)
                    }

                    R.id.AddUser -> {
                        val intent = Intent(
                            this@MainActivityAdminMotoInput,
                            MainActivityAddNewUserByAdmin::class.java
                        )
                        startActivity(intent)
                    }

                    R.id.AddHabit -> {
                        val intent = Intent(
                            this@MainActivityAdminMotoInput,
                            MainActivityAdminAddRecoHabit::class.java
                        )
                        startActivity(intent)
                    }
                }
                return true // True if the event was handled, false otherwise.
            }
        })

        //
    }

    fun saveNewMotoButton(view: View) {

        val MotoText = findViewById<EditText>(R.id.editTextEntreMotoAdmin).text.toString()
        val message = findViewById<TextView>(R.id.textViewMotoInputMessageAdmin)



        if (MotoText.isEmpty())

            message.text = "Entry is required!"
        else if (MotoText.isEmpty())

            message.text = "Feedback Content is required!"

        // Call the validation function
        val validationMessage = userInputValidator.validateMotoInput(MotoText)

        if (validationMessage != "Valid") {
            // If validation fails, show the error message and stop further execution
            message.text = validationMessage
            return
        } else { // Save data

            val newMoto = Moto(-1, MotoText, -1)
            //save data
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addMoto(newMoto)

            when (result) {

                -1 -> message.text = "Error on creating new habit"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "Moto is already exist"
                else -> {
                    message.text = "Thank you! Your Entry has been submitted successfully "
                    findViewById<Button>(R.id.buttonSubmitMotoAdmin).isEnabled = false
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