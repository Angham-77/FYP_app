package com.example.fyp_habitiny

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivityBasicEditUser : AppCompatActivity() {

    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_basic_user)

        //

        // Retrieve the username from the intent
     /*   userName = intent.getStringExtra("USERNAME") ?: ""

        val userDetails = intent.getParcelableExtra<User>("USER_DETAILS")

        if (userDetails != null) {
            // Populate the relevant EditText views with user details
            findViewById<EditText>(R.id.editTextFullNameEditUserBasic).setText(userDetails.userFullName)
            findViewById<EditText>(R.id.editTextTextEmailAddressEditUserBasic).setText(userDetails.userEmail)
            findViewById<EditText>(R.id.editTextPhoneNoEditUserBasic).setText(userDetails.userPhoneNo)
            findViewById<EditText>(R.id.editTextNewUserNameEditUserBasic).setText(userDetails.userUserName)
            //   findViewById<EditText>(R.id.editTextNewUserPasswordEditUser).setText(userDetails.userPassword)
            // Continue for other EditText views...

            // Now you have the username, you can use it to fetch user details
            //  fetchUserDetails()
            findViewById<Button>(R.id.buttonUpdateEditUserBasic).setOnClickListener {
                updateUserDetailsbasic()
            }
        }*/



        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewEditUserBasic)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this@MainActivityBasicEditUser, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_dashboard -> {
                        val intent = Intent(this@MainActivityBasicEditUser, MainActivityAddNewHabit::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this@MainActivityBasicEditUser, MainActivityMotoUserInput::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_streak -> {
                        val intent = Intent(this@MainActivityBasicEditUser, MainActivityStreakCounter::class.java)
                        startActivity(intent)
                    }
                }
                return true
            }
        })

        //

        //
        //new myDataBase.saveCurrentUserId(result, this)
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("currentUserId", -1) // -1 is the default value if no user ID is found

        val myDatabase = DataBaseHelper(this)
        val userDetailsbyUserId = myDatabase.getUserDetailsByUserId(userId)
      //  val username = myDatabase.getUserDetailsByUsername()

        if (userDetailsbyUserId != null) {
            // Populate EditText views with user details
            findViewById<EditText>(R.id.editTextFullNameEditUserBasic).setText(userDetailsbyUserId.userFullName)
            findViewById<EditText>(R.id.editTextTextEmailAddressEditUserBasic).setText(userDetailsbyUserId.userEmail)
            findViewById<EditText>(R.id.editTextPhoneNoEditUserBasic).setText(userDetailsbyUserId.userPhoneNo)
            findViewById<EditText>(R.id.editTextNewUserNameEditUserBasic).setText(userDetailsbyUserId.userUserName)

            findViewById<Button>(R.id.buttonUpdateEditUserBasic).setOnClickListener {
                updateUserDetailsbasic()
            }
        } else {
            // Handle case where user details are not found
        }
        //
    }

    fun updateUserDetailsbasic() {
        val userFullName = findViewById<EditText>(R.id.editTextFullNameEditUserBasic).text.toString()
        val userEmail = findViewById<EditText>(R.id.editTextTextEmailAddressEditUserBasic).text.toString()
        val userPhoneNo = findViewById<EditText>(R.id.editTextPhoneNoEditUserBasic).text.toString()
        val userUserName = findViewById<EditText>(R.id.editTextNewUserNameEditUserBasic).text.toString()
        val message = findViewById<TextView>(R.id.textViewMessageEditUserBasic)

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
        } else {
            // Error
            showToast("Error deleting user")
        }
    }


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}