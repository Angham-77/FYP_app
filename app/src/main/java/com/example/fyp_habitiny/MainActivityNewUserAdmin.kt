package com.example.fyp_habitiny

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.Admin
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.PasswordHasher

class MainActivityNewUserAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_admin)
    }
    fun saveNewAdminUserButton(view: View) {

        val AdminfullName = findViewById<EditText>(R.id.editTextFullNameAdmin).text.toString()
        val AdminEmail  = findViewById<EditText>(R.id.editTextTextEmailAddressAdmin).text.toString()
        val AdminUserName  = findViewById<EditText>(R.id.editTextNewUserNameAdmin).text.toString()
        val AdminPassword  = findViewById<EditText>(R.id.editTextNewUserPasswordAdmin).text.toString()
        val AdminPhoneNo = findViewById<EditText>(R.id.editTextPhoneNoAdmin).text.toString()


        val message = findViewById<TextView>(R.id.textViewMessageAdmin)
        val validationResult = UserInputValidator().validateNewUserInput(AdminfullName, AdminEmail, AdminPhoneNo, AdminUserName, AdminPassword)

        if (validationResult == "Valid") {
            // Save data
            val hashedAdminPassword = PasswordHasher.hashPassword(AdminPassword)
            val newAdmin = Admin(-1, AdminfullName, AdminEmail, AdminUserName, hashedAdminPassword , AdminPhoneNo)
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addAdmimnUser(newAdmin)

            when (result) {
                -1 -> message.text = "Error on creating a new account"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "User name is already exist"
                else -> {
                    message.text = "Your details have been added to the database successfully"
                    findViewById<Button>(R.id.buttonSaveAdmin).isEnabled = false
                }
            }
        } else {
            message.text = validationResult
        }
    }
}