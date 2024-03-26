package com.example.fyp_habitiny

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fyp_habitiny.Model.ArchiveHabit
import  com.example.fyp_habitiny.Model.DataBaseHelper
class ArchivedHabitAdapter(context: Context, resource: Int, private val archivedHabits: MutableList<ArchiveHabit>, private val dbHelper: DataBaseHelper ) :
    ArrayAdapter<ArchiveHabit>(context, resource, archivedHabits) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_sinlge_done_habit, parent, false)

        val DoneHabitNameTextView: TextView = view.findViewById(R.id.habitNameTextViewArchivedHabit)
        val DoneHabitStartDateTextView: TextView = view.findViewById(R.id.HabitStartDateTextViewArchivedHabit)
        val DoneHabitTargetTextView: TextView = view.findViewById(R.id.HabitTargetNumberArchivedHabit)
        val DoneHabitTimePrefTextView: TextView = view.findViewById(R.id.HabitTimePrefArchivedHabit)
        val DoneHabitCurrentTargetCount: TextView = view.findViewById(R.id.HabitCurrentCountArchivedHabit)
        val btnReactivate: Button = view.findViewById(R.id.buttonReactivate)
        val removeButtonArchived: ImageButton = view.findViewById(R.id.RemoveHabitBtnArchivedHabit)
        val containerLayoutArchived = view.findViewById<LinearLayout>(R.id.habitItemContainerArchivedHabit)
        val archivedHabit = getItem(position)

        DoneHabitNameTextView.text = archivedHabit?.archivedHabitName
        DoneHabitStartDateTextView.text = archivedHabit?.archivedHabitStartDate
        DoneHabitTargetTextView.text = " Target: ${archivedHabit?.archivedHabittarget}"
        DoneHabitTimePrefTextView.text = archivedHabit?.archivedHabittimePreference
        DoneHabitCurrentTargetCount.text = " Count: ${archivedHabit?.archivedHabitcurrentCount}"

        return view
    }
}