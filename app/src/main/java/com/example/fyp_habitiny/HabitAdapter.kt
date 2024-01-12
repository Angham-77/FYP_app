package com.example.fyp_habitiny

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.fyp_habitiny.Model.Habit

class HabitAdapter(context: Context, resource: Int, private val habitList: List<Habit>) :
    ArrayAdapter<Habit>(context, resource, habitList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.activity_single_habit, parent, false)

        val habitNameTextView: TextView = itemView.findViewById(R.id.habitNameTextView)
        val habitStartDateTextView: TextView = itemView.findViewById(R.id.HabitStartDateTextView)
        val habitStatusView: CheckBox = itemView.findViewById(R.id.HabitDonecheckBox)

        val habit = habitList[position]

        habitNameTextView.text = habit.habitName
        habitStartDateTextView.text = "Habit Start Date ${habit.habitStartDate}"
        habitStatusView.isChecked = (habit.habitStatus == 1)

        return itemView



    }
}