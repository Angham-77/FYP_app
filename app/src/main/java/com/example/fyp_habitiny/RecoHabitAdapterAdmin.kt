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
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.RecoHabit

class RecoHabitAdapterAdmin (context: Context, resource: Int, private val habitList: List<RecoHabit>) :
    ArrayAdapter<RecoHabit>(context, resource, habitList) {



    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(
            R.layout.activity_single_reco_habit_admin,
            parent,
            false
        )

        val recoHabitNameTextView: TextView = itemView.findViewById(R.id.RecohabitNameTextViewAdmin)
        val deleteRecoHabit: ImageButton = itemView.findViewById(R.id.imageButtonDeleteRecoHabitAdmin)
        val recohabit = habitList[position]

        recoHabitNameTextView.text = recohabit.recoHabitName

        //delete habit
        deleteRecoHabit.setOnClickListener {
            val dbHelper = DataBaseHelper(context)
            Log.d("RecoHabitAdapterAdmin", "Attempting to delete habit: ${recohabit.recohabitId}") // Log before deletion

            if (dbHelper.deleteRecoHabit(recohabit.recohabitId)) {
                Log.d("RecoHabitAdapterAdmin", "Habit deleted successfully: ${recohabit.recohabitId}") // Log on successful deletion
                // Remove the item from the list and notify the adapter to update the view
                (habitList as MutableList).removeAt(position)
                notifyDataSetChanged()
            } else {
                Log.d("RecoHabitAdapterAdmin", "Failed to delete habit: ${recohabit.recohabitId}") // Log on failure
            }
        }
        //

        return itemView
    }


}