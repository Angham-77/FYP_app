package com.example.fyp_habitiny

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.User
import com.example.fyp_habitiny.Model.PasswordHasher

class MainActivityNewUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new_user)
    }

    fun saveNewUserButton(view: View) {

        val UserfullName = findViewById<EditText>(R.id.editTextFullName).text.toString()
        val UserEmail  = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
        val UserUserName  = findViewById<EditText>(R.id.editTextNewUserName).text.toString()
        val UserPassword  = findViewById<EditText>(R.id.editTextNewUserPassword).text.toString()
        val UserPhoneNo = findViewById<EditText>(R.id.editTextPhoneNo).text.toString()
        //var UserIsActive = 0


        val message = findViewById<TextView>(R.id.textViewMessage)
        val validationResult = UserInputValidator().validateNewUserInput(UserfullName, UserEmail, UserPhoneNo, UserUserName, UserPassword)

        if (validationResult == "Valid") {
            // Save data
            val hashedPassword = PasswordHasher.hashPassword(UserPassword)
            val newUser = User(-1, UserfullName, UserEmail, UserPhoneNo, UserUserName, hashedPassword, 0)
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addUser(newUser)

            when (result) {
                -1 -> message.text = "Error on creating a new account"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "User name is already exist"
                else -> {
                    message.text = "Your details have been added to the database successfully"
                    findViewById<Button>(R.id.buttonSave).isEnabled = false
                }
            }
        } else {
            message.text = validationResult
        }
    }
}