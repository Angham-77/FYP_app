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
        var UserIsActive = 0

        val message = findViewById<TextView>(R.id.textViewMessage)

        /*check Male or Female*/
       // val rg = findViewById<RadioGroup>(R.id.radioGroupActivity)
     //   val rb = findViewById<RadioButton>(rg.checkedRadioButtonId)
     //   if(rb.text.toString() == "Male")
      //      UserIsActive = 0
      //  else UserIsActive = 1
//
        // if not empty, covert age to integer,  otherwise zero
        // val userAge = if(age.isEmpty()) 0 else age.toInt()

        if(UserfullName.isEmpty() ) // First and last name are required
        // Toast.makeText(this,"First name and last name are required!",Toast.LENGTH_LONG).show()
            message.text = "First name and last name are required!"
        else if(UserUserName.isEmpty() || UserPassword.isEmpty() ) // // User name and password are required
        //  Toast.makeText(this,"User name and Password are required!",Toast.LENGTH_LONG).show()
            message.text = "User name and Password are required!"
        else { // Save data
            // Create object
            // Hash the password using PasswordHasher
            val hashedPassword = PasswordHasher.hashPassword(UserPassword)
            println("Hashed Password: $hashedPassword")

            // Create object with hashed password
            val newUser = User(-1, UserfullName, UserEmail, UserPhoneNo, UserUserName, hashedPassword, UserIsActive)
            //save data
            val mydatabase = DataBaseHelper(this)
            val result = mydatabase.addUser(newUser)

            when(result) {

                -1 -> message.text = "Error on creating new account"
                -2 -> message.text = "Error can not open/create database"
                -3 -> message.text = "User name is already exist"
                else ->  {
                    message.text = "Your details has been add to the database successfully "
                    findViewById<Button>(R.id. buttonSave).isEnabled = false
                }
            }
        }
    }
}