package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.PasswordHasher
import com.example.fyp_habitiny.Model.User
import java.io.Serializable

class MainActivityAdminEditUserPre : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_edit_user)
    }
    //check if a user name exist and retrive the right data to be place in the right edit texts
    fun checkUserAndPopulateDetails(view: View) {
        val message = findViewById<TextView>(R.id.textViewMessagePreEditUser)
        val checkUserName = findViewById<EditText>(R.id.editTextFullNamePreEditUser).text.toString()

        if (checkUserName.isEmpty()) {
            Toast.makeText(this, "Please insert Username", Toast.LENGTH_LONG).show()
        } else {
            val myDataBase = DataBaseHelper(this)

            // Get user details by username
            val userDetails = myDataBase.getUserDetailsByUsername(checkUserName)

            if (userDetails != null) {
                // User found, navigate to the next activity
                message.text = "Details Retrieved"

                // You can pass the user details to the next activity for further operations
                val intent = Intent(this, MainActivityAdminEdtUser::class.java)
                intent.putExtra("USER_DETAILS", userDetails as Serializable)
                startActivity(intent)

            } else {
                message.text = "User Not Found, Please try again"
            }
        }
    }

    fun getUserButton(view: View) {

        val message = findViewById<TextView>(R.id.textViewMessagePreEditUser)
        val CheckuserName = findViewById<EditText>(R.id.editTextFullNamePreEditUser).text.toString()


        if (CheckuserName.isEmpty()) {
            Toast.makeText(this, "Please insert Username", Toast.LENGTH_LONG).show()
        } else {
            val myDataBase = DataBaseHelper(this)

            // Verify the password
            val result = myDataBase.getUser(User(-1, "", "", "", CheckuserName, ""))
            println("DataBase =  $result")

            when (result) {
                -1 -> message.text = "User Not Found, Please try again"
                -2 -> message.text = "Error Cannot Open/Create DataBase"
                in 1..Int.MAX_VALUE -> {
                    message.text = "Details Retrieved"

                    // Launch the new activity
                    val intent = Intent(this, MainActivityAdminEdtUser::class.java)
                    startActivity(intent)
                }
                else -> message.text = "Incorrect username"
            }
        }
    }
    fun removeUser(view: View) {
        val userName = findViewById<EditText>(R.id.editTextFullNamePreEditUser).text.toString()

        if (userName.isEmpty()) {
            showToast("Please insert Username")
            return
        }

        // Delete the user from the database
        val myDatabase = DataBaseHelper(this)
        Log.d("UserNameToDelete", userName)
        val isUserDeleted = myDatabase.deleteUser(userName)

        if (isUserDeleted) {
            // Success
            showToast("User deleted successfully")
            // Optionally, you can navigate back to the previous screen or perform other actions
        } else {
            // Error
            showToast("Error deleting user")
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



}