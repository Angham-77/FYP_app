package com.example.fyp_habitiny

import android.icu.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

class UserInputValidator {
    fun validateNewUserInput(fullName: String, email: String, phoneNo: String, userName: String, userPassword: String): String {
        if (fullName.isEmpty()) {
            return "Full name is required!"
        } else if (fullName.length < 3 || fullName.length > 50) {
            return "Full name must be between 3 and 50 characters!"
        }

        if (email.isEmpty()) {
            return "Email is required!"
        } else {
            val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
            if (!emailPattern.matches(email)) {
                return "Invalid email format.!"
            }
        }


        if (phoneNo.isEmpty()) {
            return "Phone No is required!"
        } else {
            val phonePattern = Pattern.compile("\\d{11}")
            if (!phonePattern.matcher(phoneNo).matches()) {
                return "Invalid phone number format. It should have 10 digits.!"
            }
        }

        if (userName.isEmpty() || userPassword.isEmpty()) {
            return "Username and Password are required!"
        } else if (userName.length < 4 || userName.length > 20) {
            return "Username must be between 5 and 20 characters."
        }

        return "Valid"
    }

    fun validateHabitInput(habitName: String, habitStartDate: String): String {
        if (habitName.isEmpty()) {
            return "Habit name is required!"
        }
        else if (habitName.length < 3 || habitName.length > 20){
            return "Habit must be between 3 and 20 characters."
        }
        if (habitStartDate.isEmpty()) {
            return "Start date is required!"
        } else {
            //Date validation to ensure a conssitent format
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
            dateFormat.isLenient = false
            try {
                dateFormat.parse(habitStartDate)
            } catch (e: Exception) {
                return "Invalid start date format. Expected format: dd-MM-yyyy"
            }
        }
        return "Valid"
    }
}