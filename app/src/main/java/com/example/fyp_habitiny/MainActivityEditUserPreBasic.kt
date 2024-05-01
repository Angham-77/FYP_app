package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.io.Serializable

class MainActivityEditUserPreBasic : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_edit_user_basicuser)

        //nav
        val navView: BottomNavigationView = findViewById(R.id.nav_viewEditUsePreBasic)
        navView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val intent = Intent(this@MainActivityEditUserPreBasic, MainActivtyReadyBtn::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_dashboard -> {
                        val intent = Intent(this@MainActivityEditUserPreBasic, MainActivityAddNewHabit::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this@MainActivityEditUserPreBasic, MainActivityMotoUserInput::class.java)
                        startActivity(intent)
                    }
                    R.id.navigation_streak -> {
                        val intent = Intent(this@MainActivityEditUserPreBasic, MainActivityStreakCounter::class.java)
                        startActivity(intent)
                    }
                }
                return true
            }
        })

        //

    }

    fun checkUserAndPopulateDetailsBasic(view: View) {
        val message = findViewById<TextView>(R.id.textViewMessagePreEditUserBasic)
        val checkUserName = findViewById<EditText>(R.id.editTextFullNamePreEditUserBasic).text.toString()

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
                val intent = Intent(this, MainActivityBasicEditUser::class.java)
                intent.putExtra("USER_DETAILS", userDetails as Serializable)
                startActivity(intent)

            } else {
                message.text = "User Not Found, Please try again"
            }
        }
    }

    fun removeUserBasic(view: View) {
        val userName = findViewById<EditText>(R.id.editTextFullNamePreEditUserBasic).text.toString()

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
        } else {
            // Error
            showToast("Error deleting user")
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}