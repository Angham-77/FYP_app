package com.example.fyp_habitiny

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserInputValidatorTest {
    private lateinit var validator: UserInputValidator

    @Before
    fun setUp() {
        validator = UserInputValidator()
    }

    @Test
    fun validateNewUserInput_validInput_returnsValid() {
        val result = validator.validateNewUserInput(
            "John Doe",
            "john@gmail.com",
            "01234567890",
            "johndoe",
            "password"
        )
        assertEquals("Valid", result)
    }

    @Test
    fun validateNewUserInput_emptyFullName_returnsFullNameRequired() {
        val result = validator.validateNewUserInput(
            "",
            "john@example.com",
            "01234567890",
            "johndoe",
            "password"
        )
        assertEquals("Full name is required!", result)
    }

    @Test
    fun validateNewUserInput_FullNamelimits_returnsFullNameinvalid() {
        val result = validator.validateNewUserInput(
            "Jo",
            "john@example.com",
            "01234567890",
            "johndoe",
            "password"
        )
        assertEquals("Full name must be between 3 and 50 characters!", result)
    }

    @Test
    fun validateNewUserInput_emptyUserName_returnsUserNameRequired() {
        val result = validator.validateNewUserInput(
            "John Doe",
            "john@example.com",
            "01234567890",
            "",
            "password"
        )
        assertEquals("Username and Password are required!", result)
    }

    @Test
    fun validateNewUserInput_emptyPassword_returnsPasswordRequired() {
        val result = validator.validateNewUserInput(
            "John Doe",
            "john@example.com",
            "01234567890",
            "johndoe",
            ""
        )
        assertEquals("Username and Password are required!", result)
    }

    @Test
    fun validateNewUserInput_UserNameLimets_returnsusernameBtween5and20() {
        val result = validator.validateNewUserInput(
            "John Doe",
            "john@example.com",
            "01234567890",
            "joh",
            "password"
        )
        assertEquals("Username must be between 5 and 20 characters.", result)
    }

    @Test
    fun validateNewUserInput_PhoneNoFormat_PhoneNoMustbe10Digits() {
        val result = validator.validateNewUserInput(
            "John Doe",
            "john@example.com",
            "0123456789000",
            "johndoe",
            "password"
        )
        assertEquals("Invalid phone number format. It should have 10 digits.!", result)
    }

    @Test
    fun validateNewUserInput_emptyPhoneNo_returnPhoneNoisRequired() {
        val result = validator.validateNewUserInput(
            "John Doe",
            "john@example.com",
            "",
            "johndoe",
            "password"
        )
        assertEquals("Phone No is required!", result)
    }

    @Test
    fun validateNewUserInput_emptyEmail_returnEmailisRequired() {
        val result =
            validator.validateNewUserInput("John Doe", "", "01234567890", "johndoe", "password")
        assertEquals("Email is required!", result)
    }

    @Test
    fun validateNewUserInput_EmailFormat_returnEmailisNotCorrect() {
        val result = validator.validateNewUserInput(
            "John Doe",
            "johnexample.com",
            "01234567890",
            "johndoe",
            "password"
        )
        assertEquals("Invalid email format.!", result)
    }
}
