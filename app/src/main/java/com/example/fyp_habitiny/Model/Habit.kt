package com.example.fyp_habitiny.Model

import android.database.Cursor

data class Habit  (val habitId: Int, var HabitUserId: Int, var habitName: String, var habitStartDate: String,
                   val habitStatus: Int, val habittarget: Int, val habittimePreference: String, var habitcurrentCount: Int) {


    constructor(cursor: Cursor) : this(
        habitId = cursor.getInt(cursor.getColumnIndexOrThrow("HabitId")),
        HabitUserId = cursor.getInt(cursor.getColumnIndexOrThrow("UserId")), // Assuming there's a UserId column
        habitName = cursor.getString(cursor.getColumnIndexOrThrow("HabitName")),
        habitStartDate = cursor.getString(cursor.getColumnIndexOrThrow("StartDate")),
        habitStatus = cursor.getInt(cursor.getColumnIndexOrThrow("HabitStatus")),
        habittarget = cursor.getInt(cursor.getColumnIndexOrThrow("Target")),
        habittimePreference = cursor.getString(cursor.getColumnIndexOrThrow("TimePreference")),
        habitcurrentCount = cursor.getInt(cursor.getColumnIndexOrThrow("CurrentTargetCount"))
    )
}