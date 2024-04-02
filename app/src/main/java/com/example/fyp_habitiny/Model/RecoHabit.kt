package com.example.fyp_habitiny.Model

import android.database.Cursor

data class RecoHabit  (val recohabitId: Int, var recoHabitName: String, val recoHabitAdminId: Int)
{


    constructor(cursor: Cursor) : this(
        recohabitId = cursor.getInt(cursor.getColumnIndexOrThrow("RecoHabitId")),
        recoHabitName = cursor.getString(cursor.getColumnIndexOrThrow("RecoHabitName")),
        recoHabitAdminId = cursor.getInt(cursor.getColumnIndexOrThrow("RecoAdminId")),

    )
}