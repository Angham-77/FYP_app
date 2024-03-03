package com.example.fyp_habitiny

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.PasswordHasher
import com.example.fyp_habitiny.Model.User

class MainActivityAdminEdtUser : AppCompatActivity() {

    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_admin)

        // Retrieve the username from the intent
        userName = intent.getStringExtra("USERNAME") ?: ""

        val userDetails = intent.getParcelableExtra<User>("USER_DETAILS")

        if (userDetails != null) {
            // Populate the relevant EditText views with user details
            findViewById<EditText>(R.id.editTextFullNameEditUser).setText(userDetails.userFullName)
            findViewById<EditText>(R.id.editTextTextEmailAddressEditUser).setText(userDetails.userEmail)
            findViewById<EditText>(R.id.editTextPhoneNoEditUser).setText(userDetails.userPhoneNo)
            findViewById<EditText>(R.id.editTextNewUserNameEditUser).setText(userDetails.userUserName)
         //   findViewById<EditText>(R.id.editTextNewUserPasswordEditUser).setText(userDetails.userPassword)
            // Continue for other EditText views...

            // Now you have the username, you can use it to fetch user details
            //  fetchUserDetails()
            findViewById<Button>(R.id.buttonUpdateEditUser).setOnClickListener {
                updateUserDetails()
            }
        }
    }
     fun updateUserDetails() {
        val userFullName = findViewById<EditText>(R.id.editTextFullNameEditUser).text.toString()
        val userEmail = findViewById<EditText>(R.id.editTextTextEmailAddressEditUser).text.toString()
        val userPhoneNo = findViewById<EditText>(R.id.editTextPhoneNoEditUser).text.toString()
        val userUserName = findViewById<EditText>(R.id.editTextNewUserNameEditUser).text.toString()
        val message = findViewById<TextView>(R.id.textViewMessageEditUser)

        // Validate the user details
        val validator = UserInputValidator()
        val validationMessage = validator.validateEditUserInput(userFullName, userEmail, userPhoneNo, userUserName)

        if (validationMessage == "Valid") {
            // Create a User object with updated details
            val updatedUser = User(-1, userFullName, userEmail, userPhoneNo, userUserName, "")

            // Update the details in the database
            val myDatabase = DataBaseHelper(this)
            val result = myDatabase.updateUserDetails(updatedUser)

            if (result > 0) {
                // Success
                showToast("User details updated successfully")
            } else {
                // Error
                showToast("Error updating user details")
            }
        } else {
            // Display validation error message
            message.text = validationMessage
            showToast("Validation Error: $validationMessage")
        }
    }
     fun removeUser(view: View) {
        // Retrieve the username from the intent
        val userName = intent.getStringExtra("USERNAME") ?: ""


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
