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
                return "Invalid phone number format. It should have 11 digits.!"
            }
        }

        if (userName.isEmpty() || userPassword.isEmpty()) {
            return "Username and Password are required!"
        } else if (userName.length < 4 || userName.length > 20) {
            return "Username must be between 4 and 20 characters."
        }

        return "Valid"
    }

    fun validateHabitInput(habitName: String, habitStartDate: String,  habitEndDate: String, habitTarget: Int): String {
        if (habitName.isEmpty()) {
            return "Habit name is required!"
        }
        else if (habitName.length < 3 || habitName.length > 50) { // Updated the max length to 50 characters
            return "Habit name must be between 3 and 50 characters."
        }
        if (habitStartDate.isEmpty()) {
            return "Start date is required!"
        }
        if (habitEndDate.isEmpty()) {
            return "End date is required!"
        }

        if (habitTarget !in 1..50) {
            return "Target must be a positive number and cannot exceed 50."
        }

        return "Valid"
    }
    fun validateFeedbackInput(feedback: String): String {
        if (feedback.isEmpty()) {
            return "Feedback is required!"
        }
        else if (feedback.length < 4 || feedback.length > 50) {
            return "Feedback entry must be between 4 and 50 characters."
        }
        return "Valid"
    }
    fun validateRecoHabitInput(recoHabit: String): String {
        if (recoHabit.isEmpty()) {
            return "Habit is required!"
        }
        else if (recoHabit.length < 3 || recoHabit.length > 20) {
            return "Habit entry must be between 3 and 20 characters."
        }
        return "Valid"
    }
    fun validateMotoInput(motoText: String): String {
        if (motoText.isEmpty()) {
            return "Text entry is required!"
        }
        else if (motoText.length < 4 || motoText.length > 100) {
            return "Text entry must be between 4 and 50 characters."
        }
        return "Valid"
    }
    fun validateEditUserInput(fullName: String, email: String, phoneNo: String, userName: String): String {
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
                return "Invalid phone number format. It should have 11 digits.!"
            }
        }

        if (userName.isEmpty()) {
            return "Username and Password are required!"
        } else if (userName.length < 4 || userName.length > 20) {
            return "Username must be between 4 and 20 characters."
        }

        return "Valid"
    }
}