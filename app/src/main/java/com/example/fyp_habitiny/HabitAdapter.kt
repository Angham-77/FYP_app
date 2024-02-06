package com.example.fyp_habitiny

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Habit

class HabitAdapter(context: Context, resource: Int, private val habitList: List<Habit>) :
    ArrayAdapter<Habit>(context, resource, habitList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.activity_single_habit, parent, false) // Ensure this matches your layout file name

        val habitNameTextView: TextView = itemView.findViewById(R.id.habitNameTextView)
        val habitStartDateTextView: TextView = itemView.findViewById(R.id.HabitStartDateTextView)
        val habitStatusView: CheckBox = itemView.findViewById(R.id.HabitDonecheckBox)
        val removeButton: Button = itemView.findViewById(R.id.RemoveHabitBtn)

        val habit = habitList[position]

        habitNameTextView.text = habit.habitName
        habitStartDateTextView.text = "Start Date: ${habit.habitStartDate}"
        habitStatusView.isChecked = (habit.habitStatus == 1)

        removeButton.setOnClickListener {
            val dbHelper = DataBaseHelper(context)
            Log.d("HabitAdapter", "Attempting to delete habit: ${habit.habitId}") // Log before deletion

            if (dbHelper.deleteHabit(habit.habitId)) {
                Log.d("HabitAdapter", "Habit deleted successfully: ${habit.habitId}") // Log on successful deletion
                // Remove the item from the list and notify the adapter to update the view
                (habitList as MutableList).removeAt(position)
                notifyDataSetChanged()
            } else {
                Log.d("HabitAdapter", "Failed to delete habit: ${habit.habitId}") // Log on failure
            }
        }

        return itemView
    }

}