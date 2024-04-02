package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.fyp_habitiny.Model.ArchiveHabit
import com.example.fyp_habitiny.Model.Habit
import com.example.fyp_habitiny.Model.RecoHabit

class RecoHabitAdapter (context: Context, resource: Int, private val habitList: MutableList<RecoHabit>, private val listener: RecoHabitClickListener) :
    ArrayAdapter<RecoHabit>(context, resource, habitList) {


    interface RecoHabitClickListener {
        fun onAddRecoHabitClicked(recoHabitName: String)
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.activity_single_recohabit, parent, false)

        val recoHabitNameTextView: TextView = itemView.findViewById(R.id.RecohabitNameTextView)
        val addRecoHabit: ImageButton = itemView.findViewById(R.id.imageButtonAddRecoHabit)
        val recohabit = habitList[position]

        recoHabitNameTextView.text = recohabit.recoHabitName

        //set a click listner to add the reco habit to the right edit text
        addRecoHabit.setOnClickListener {
            val recoHabitName = habitList[position].recoHabitName
            listener.onAddRecoHabitClicked(recoHabitName)
        }
        //

        return itemView
    }
    fun updateData(newHabitList: List<RecoHabit>) {
        Log.d("RecoHabitAdapter", "Updating data with new list size: ${newHabitList.size}")
        habitList.clear()
        habitList.addAll(newHabitList)
        Log.d("RecoHabitAdapter", "New data set size after update: ${habitList.size}")
        notifyDataSetChanged()
    }


}