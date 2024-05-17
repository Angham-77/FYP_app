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
        assertEquals("Username must be between 4 and 20 characters.", result)
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
        assertEquals("Invalid phone number format. It should have 11 digits.!", result)
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
    //Habit input
    @Test
    fun validateHabitInput_HabitNameRequired_returnHabitNameRequired() {
        val result = validator.validateHabitInput(
            "",
            "12/02/2024",
            "13/02/2024",
            1,
        )
        assertEquals("Habit name is required!", result)
    }
    @Test
    fun validateHabitInput_HabitNameLength_returnHbabitNameLength() {
        val result = validator.validateHabitInput(
            "Ru",
            "12/02/2024",
            "13/02/2024",
            1,
        )
        assertEquals("Habit name must be between 3 and 50 characters.", result)
    }
    @Test
    fun validateHabitInput_HabitNameLength_returnHabitNameValid() {
        val result = validator.validateHabitInput(
            "Run",
            "12/02/2024",
            "13/02/2024",
            1,
        )
        assertEquals("Valid", result)
    }
    @Test
    fun validateHabitInput_HabitStartDateEmpty_returnHbabitStartDateRequired() {
        val result = validator.validateHabitInput(
            "Run",
            "",
            "13/02/2024",
            1,
        )
        assertEquals("Start date is required!", result)
    }
    @Test
    fun validateHabitInput_HabitEndDateEmpty_returnHbabitEndDateRequired() {
        val result = validator.validateHabitInput(
            "Run",
            "13/02/2024",
            "",
            1,
        )
        assertEquals("End date is required!", result)
    }
    @Test
    fun validateTargetInput_HabitTargetInput_returnHbabitTargetMustNotExceed() {
        val result = validator.validateHabitInput(
            "Run",
            "13/02/2024",
            "13/02/2024",
            55,
        )
        assertEquals("Target must be a positive number and cannot exceed 50.", result)
    }
    //feedback
    @Test
    fun validateFeedbackInput_validateFeedbackRequired_returnvalidateFeedbackRequired() {
        val result = validator.validateFeedbackInput(
            "",
        )
        assertEquals("Feedback is required!", result)
    }
    @Test
    fun validateFeedbackInput_validateFeedbackLength_returnvalidateFeedbackLength() {
        val result = validator.validateFeedbackInput(
            "Tes",
        )
        assertEquals("Feedback entry must be between 4 and 50 characters.", result)
    }
    @Test
    fun validateFeedbackInput_validateFeedbackLength_returnvalidateFeedbackLengthOK() {
        val result = validator.validateFeedbackInput(
            "Test",
        )
        assertEquals("Valid", result)
    }
    //RecoHabit
    @Test
    fun validateRecoHabitInput_RecoHabitRequired_returnRecoHabitRequired() {
        val result = validator.validateRecoHabitInput(
            "",
        )
        assertEquals("Habit is required!", result)
    }
    @Test
    fun validateRecoHabitInput_RecoHabitRequired_returnRecoHabitLenghth() {
        val result = validator.validateRecoHabitInput(
            "Ru",
        )
        assertEquals("Habit entry must be between 3 and 20 characters.", result)
    }
    @Test
    fun validateRecoHabitInput_RecoHabitRequired_returnRecoHabitValid() {
        val result = validator.validateRecoHabitInput(
            "Run",
        )
        assertEquals("Valid", result)
    }
    //Moto
    @Test
    fun validateMotoInput_MotoRequired_returnMotoRequired() {
        val result = validator.validateMotoInput(
            "",
        )
        assertEquals("Text entry is required!", result)
    }
    @Test
    fun validateMotoInput_MotoRequired_returnMotoLength() {
        val result = validator.validateMotoInput(
            "Tes",
        )
        assertEquals("Text entry must be between 4 and 50 characters.", result)
    }
    @Test
    fun validateMotoInput_MotoValid_returnMotoValid() {
        val result = validator.validateMotoInput(
            "Test",
        )
        assertEquals("Valid", result)
    }
    //Edit User
    @Test
    fun validateEditUserInput_validInput_returnsValid() {
        val result = validator.validateEditUserInput(
            "John Doe",
            "john@gmail.com",
            "01234567890",
            "johndoe",
        )
        assertEquals("Valid", result)
    }

    @Test
    fun validateEditUserInput_emptyFullName_returnsFullNameRequired() {
        val result = validator.validateEditUserInput(
            "",
            "john@example.com",
            "01234567890",
            "johndoe",
        )
        assertEquals("Full name is required!", result)
    }

    @Test
    fun validateEditUserInput_FullNamelimits_returnsFullNameinvalid() {
        val result = validator.validateEditUserInput(
            "Jo",
            "john@example.com",
            "01234567890",
            "johndoe",
        )
        assertEquals("Full name must be between 3 and 50 characters!", result)
    }

    @Test
    fun validateEditUserInput_emptyUserName_returnsUserNameRequired() {
        val result = validator.validateEditUserInput(
            "John Doe",
            "john@example.com",
            "01234567890",
            "",
        )
        assertEquals("Username and Password are required!", result)
    }

    @Test
    fun validateEditUserInput_UserNameLimets_returnsusernameBtween5and20() {
        val result = validator.validateEditUserInput(
            "John Doe",
            "john@example.com",
            "01234567890",
            "joh",
        )
        assertEquals("Username must be between 4 and 20 characters.", result)
    }

    @Test
    fun validateEditUserInput_PhoneNoFormat_PhoneNoMustbe10Digits() {
        val result = validator.validateEditUserInput(
            "John Doe",
            "john@example.com",
            "0123456789000",
            "johndoe",
        )
        assertEquals("Invalid phone number format. It should have 11 digits.!", result)
    }

    @Test
    fun validateEditUserInput_emptyPhoneNo_returnPhoneNoisRequired() {
        val result = validator.validateEditUserInput(
            "John Doe",
            "john@example.com",
            "",
            "johndoe",
        )
        assertEquals("Phone No is required!", result)
    }

    @Test
    fun validateEditUserInput_emptyEmail_returnEmailisRequired() {
        val result =
            validator.validateEditUserInput("John Doe", "", "01234567890", "johndoe")
        assertEquals("Email is required!", result)
    }

    @Test
    fun validateEditUserInput_EmailFormat_returnEmailisNotCorrect() {
        val result = validator.validateEditUserInput(
            "John Doe",
            "johnexample.com",
            "01234567890",
            "johndoe",
        )
        assertEquals("Invalid email format.!", result)
    }
}
