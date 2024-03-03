package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.PasswordHasher
import com.example.fyp_habitiny.Model.User

class MainActivityAddNewUserByAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user_by_admin)
    }

    fun saveNewUserButtonByAdmin(view: View) {

        val UserfullName = findViewById<EditText>(R.id.editTextFullNameByAdmin).text.toString()
        val UserEmail  = findViewById<EditText>(R.id.editTextTextEmailAddressByAdmin).text.toString()
        val UserUserName  = findViewById<EditText>(R.id.editTextNewUserNameByAdmin).text.toString()
        val UserPassword  = findViewById<EditText>(R.id.editTextNewUserPasswordByAdmin).text.toString()
        val UserPhoneNo = findViewById<EditText>(R.id.editTextPhoneNoByAdmin).text.toString()
        //var UserIsActive = 0


        val message = findViewById<TextView>(R.id.textViewMessageByAdmin)
        val validationResult = UserInputValidator().validateNewUserInput(UserfullName, UserEmail, UserPhoneNo, UserUserName, UserPassword)

        if (validationResult == "Valid") {
            // Save data
            val hashedPassword = PasswordHasher.hashPassword(UserPassword)
            val newUser = User(-1, UserfullName, UserEmail, UserPhoneNo, UserUserName, hashedPassword)
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addUser(newUser)

            when (result) {
                -1 -> message.text = "Error on creating a new account"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "User name is already exist"
                else -> {
                    message.text = "Your details have been added to the database successfully"
                    findViewById<Button>(R.id.buttonSaveByAdmin).isEnabled = false
                }
            }
        } else {
            message.text = validationResult
        }
    }
}