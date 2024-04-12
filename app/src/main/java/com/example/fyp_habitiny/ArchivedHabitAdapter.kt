package com.example.fyp_habitiny

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.fyp_habitiny.Model.ArchiveHabit
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Habit

class ArchivedHabitAdapter(context: Context, resource: Int, private val archivedHabits: MutableList<ArchiveHabit>, private val dbHelper: DataBaseHelper) :
    ArrayAdapter<ArchiveHabit>(context, resource, archivedHabits) {

    private var onReactivateHabitListener: OnReactivateHabitListener? = null

    //1 (of 4 steps) Define an interface for thr Reactive button
    interface OnReactivateHabitListener {
        fun onReactivateHabit(habitName: String, habitTarget: Int)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_sinlge_done_habit, parent, false)

        val doneHabitNameTextView: TextView = view.findViewById(R.id.habitNameTextViewArchivedHabit)
        val doneHabitStartDateTextView: TextView = view.findViewById(R.id.HabitStartDateTextViewArchivedHabit)
        val doneHabitEndtDateTextView: TextView = view.findViewById(R.id.HabitEndtDateTextViewArchivedHabit)
        val doneHabitTargetTextView: TextView = view.findViewById(R.id.HabitTargetNumberArchivedHabit)
        val doneHabitTimePrefTextView: TextView = view.findViewById(R.id.HabitTimePrefArchivedHabit)
        val doneHabitCurrentTargetCount: TextView = view.findViewById(R.id.HabitCurrentCountArchivedHabit)
        val btnReactivate: Button = view.findViewById(R.id.buttonReactivate)
        val btnDeleteArchive: ImageButton  = view.findViewById(R.id.RemoveHabitBtnArchivedHabit)

        //val archivedHabit = getItem(position)
        val archivedHabit = archivedHabits[position]

        doneHabitNameTextView.text = archivedHabit?.archivedHabitName
        doneHabitStartDateTextView.text = "Start Date: ${archivedHabit?.archivedHabitStartDate}"
        doneHabitEndtDateTextView.text = "End Date: ${archivedHabit?.archivedEndDate}"
        doneHabitTargetTextView.text = "Target: ${archivedHabit?.archivedHabittarget}"
        doneHabitTimePrefTextView.text = "Time: ${archivedHabit?.archivedHabittimePreference}"
        doneHabitCurrentTargetCount.text = "Count: ${archivedHabit?.archivedHabitcurrentCount}"


        //3 Set up a click listener in the adapter
        btnReactivate.setOnClickListener {
            Log.d("ArchivedHabitAdapter", "Reactivate button clicked")
          //  Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show()
            archivedHabit?.let {
                onReactivateHabitListener?.onReactivateHabit(it.archivedHabitName, it.archivedHabittarget)
            }
        }

        btnDeleteArchive.setOnClickListener {
            val dbHelper = DataBaseHelper(context)
            Log.d("HabitAdapter", "Attempting to delete habit: ${archivedHabit.archivedHabitId}") // Log before deletion

            if (dbHelper.deletearchivedHabit(archivedHabit.archivedHabitId)) {
                Log.d("HabitAdapter", "Habit deleted successfully: ${archivedHabit.archivedHabitId}") // Log on successful deletion
                // Remove the item from the list and notify the adapter to update the view
                (archivedHabits as MutableList).removeAt(position)
                notifyDataSetChanged()
            } else {
                Log.d("HabitAdapter", "Failed to delete habit: ${archivedHabit.archivedHabitId}") // Log on failure
            }
        }

        return view
    }

    fun setOnReactivateHabitListener(listener: OnReactivateHabitListener) {
        this.onReactivateHabitListener = listener
    }
    fun updateData(newHabitList: List<ArchiveHabit>) {
        Log.d("HabitAdapter", "Updating data with new list size: ${newHabitList.size}")
        archivedHabits.clear()
        archivedHabits.addAll(newHabitList)
        Log.d("HabitAdapter", "New data set size after update: ${archivedHabits.size}")
        notifyDataSetChanged()
    }

}
