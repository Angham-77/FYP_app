package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.Admin
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.PasswordHasher
import com.example.fyp_habitiny.Model.User

class MainActivityAdminLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
    }
    fun adminLoginButton(view: View) {
        val message = findViewById<TextView>(R.id.textViewMessageMainActivityAdmin)
        val adminUserName = findViewById<EditText>(R.id.editTextAdminUserName).text.toString()
        val adminPassword = findViewById<EditText>(R.id.editTextPasswordAdmin).text.toString()

        if (adminUserName.isEmpty() || adminPassword.isEmpty()) {
            Toast.makeText(this, "Please insert Username and Password", Toast.LENGTH_LONG).show()
        } else {
            val myDataBase = DataBaseHelper(this)

            // Hash the entered password
            val hashedEnteredPassword = PasswordHasher.hashPassword(adminPassword)
            println("Hashed password: $hashedEnteredPassword")

            // Verify the password
            val result = myDataBase.getAdminUser(Admin(-1, "", "", adminUserName, hashedEnteredPassword, ""))
            println("DataBase =  $result")

            when (result) {
                -1 -> message.text = "User Not Found, Please try again"
                -2 -> message.text = "Error Cannot Open/Create DataBase"
                in 1..Int.MAX_VALUE -> {
                    message.text = "You logged in successfully"

                    // Launch the new activity
                    val intent = Intent(this, MainActivtyReadyBtn::class.java)
                    startActivity(intent)
                }
                else -> message.text = "Incorrect username or password"
            }
        }
    }
    fun goToAdminRegister(view: View){
        val intent = Intent(this, MainActivityNewUserAdmin::class.java)
        startActivity(intent)
    }
}