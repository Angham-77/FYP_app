package com.example.fyp_habitiny.Model

import android.database.Cursor

data class ArchiveHabit( val archivedHabitId: Int, var archivedHabitUserId: Int, var AhabitId: Int, var archivedHabitName: String, var archivedHabitStartDate: String,
                        val archivedHabittarget: Int, val archivedHabittimePreference: String, var archivedHabitcurrentCount: Int, val archivedEndDate: String){

    constructor(cursor: Cursor) : this(
        archivedHabitId = cursor.getInt(cursor.getColumnIndexOrThrow("ArchiveHabitId")),
        archivedHabitUserId = cursor.getInt(cursor.getColumnIndexOrThrow("ArchiveUserId")),
        AhabitId = cursor.getInt(cursor.getColumnIndexOrThrow("HabitId")),
        archivedHabitName = cursor.getString(cursor.getColumnIndexOrThrow("ArchiveHabitName")),
        archivedHabitStartDate = cursor.getString(cursor.getColumnIndexOrThrow("ArchiveStartDate")),
        archivedHabittarget = cursor.getInt(cursor.getColumnIndexOrThrow("ArchiveTarget")),
        archivedHabittimePreference = cursor.getString(cursor.getColumnIndexOrThrow("ArchiveTimePreference")),
        archivedHabitcurrentCount = cursor.getInt(cursor.getColumnIndexOrThrow("ArchiveCurrentTargetCount")),
        archivedEndDate = cursor.getString(cursor.getColumnIndexOrThrow("EndDate"))
    )
}
