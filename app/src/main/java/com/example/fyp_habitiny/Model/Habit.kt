package com.example.fyp_habitiny.Model

data class Habit  (val habitId: Int, var HabitUserId: Int, var habitName: String, var habitStartDate: String,
                   val habitStatus: Int, val habittarget: Int, val habittimePreference: String, var habitcurrentCount: Int) {
}