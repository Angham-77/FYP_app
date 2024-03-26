package com.example.fyp_habitiny

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.fyp_habitiny.Model.DataBaseHelper
import com.example.fyp_habitiny.Model.Habit

class HabitAdapter(context: Context, resource: Int, private val habitList: List<Habit>, private val dbHelper: DataBaseHelper) :
    ArrayAdapter<Habit>(context, resource, habitList) {


    var addHabitToArchiveListener: ((Habit) -> Unit)? = null
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.activity_single_habit, parent, false) // Ensure this matches your layout file name

        val habitNameTextView: TextView = itemView.findViewById(R.id.habitNameTextView)
        val habitStartDateTextView: TextView = itemView.findViewById(R.id.HabitStartDateTextView)
        val habitTargetTextView: TextView= itemView.findViewById(R.id.HabitTargetNumber)
        val habitTimePrefTextView: TextView = itemView.findViewById(R.id.HabitTimePref)
        val habitCurrentTargetCount: TextView = itemView.findViewById(R.id.HabitCurrentCount)
        val btnDone: Button = itemView.findViewById(R.id.buttonDone)
        val removeButton: ImageButton = itemView.findViewById(R.id.RemoveHabitBtn)
        val increaseButton: ImageButton = itemView.findViewById(R.id.imageButtonTargetIncrease)
        val decreaseButton: ImageButton = itemView.findViewById((R.id.imageButtonTargetDecrease))
        val containerLayout = itemView.findViewById<LinearLayout>(R.id.habitItemContainer)


        val habit = habitList[position]

        habitNameTextView.text = habit.habitName
        habitStartDateTextView.text = "Start Date: ${habit.habitStartDate}"
        habitTargetTextView.text = "Target: ${habit.habittarget}"
        habitTimePrefTextView.text = "Time: ${habit.habittimePreference}"
        habitCurrentTargetCount.text = "Current Count: ${habit.habitcurrentCount}"

        // Check if the target is met and change the background color accordingly
        if (habit.habitcurrentCount == habit.habittarget) {
            // Target met, change background color
            containerLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.TargetMet)) // Example color
            //textView.setTextColor(ContextCompat.getColor(context, R.color.YOUR_DEFAULT_TEXT_COLOR));
        }

        //habitStatusView.isChecked = (habit.habitStatus == 1)

        /////////////////
        increaseButton.setOnClickListener {
            if (habit.habitcurrentCount < habit.habittarget) {
                habit.habitcurrentCount ++

                // Update the text view displaying the current count
                // habitTargetTextView.text = "Target: ${habit.habitcurrentCount} / ${habit.habittarget}"
                habitCurrentTargetCount.text = "Current Count: ${habit.habitcurrentCount}"



                // Update the habit in the database with the new current count
                val dbHelper = DataBaseHelper(context)
                dbHelper.updateHabitProgress(habit.habitId, habit.habitcurrentCount)

                Log.d("HabitAdapter", "Increased target for habit: ${habit.habitId} to ${habit.habitcurrentCount}")
                // Check if the target is met and change the background color accordingly
                if (habit.habitcurrentCount == habit.habittarget) {
                    // Target met, change background color
                    containerLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.TargetMet)) // Example color

                    //
                    // Target met, show alert dialog
                    val dialogBuilder = AlertDialog.Builder(context)
                    dialogBuilder.setMessage("Congratulations! You've met your target for  ${habit.habitName} \uD83C\uDF89.")
                        .setCancelable(false)
                        .setPositiveButton("OK") { dialog, id ->
                            // If you want to do something when the user clicks OK, add it here
                            dialog.dismiss()
                        }
                    val alert = dialogBuilder.create()
                    alert.setTitle("Target Met")
                    alert.show()
                    //
                }
                // Check if the target is met to optionally disable the button
                if (habit.habitcurrentCount == habit.habittarget) {
                    increaseButton.isEnabled = false // Disable the button
                    Log.d("HabitAdapter", "Target met for habit: ${habit.habittarget}")
                }
            } else {

                increaseButton.isEnabled = false
            }
            //    this.notifyDataSetChanged()
        }

        decreaseButton.setOnClickListener {
            if (habit.habitcurrentCount > 0) {
                habit.habitcurrentCount--

                // Update the text view displaying the current count
                habitCurrentTargetCount.text = "Current Count: ${habit.habitcurrentCount}"

                // Update the habit in the database with the new current count
                val dbHelper = DataBaseHelper(context)
                dbHelper.updateHabitProgress(habit.habitId, habit.habitcurrentCount)

                // Check if the target was met and revert background color to default if previously met
                if (habit.habitcurrentCount < habit.habittarget) {
                    containerLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.singlHabit)) // Default color
                }

                // Enable decrease button if current count is not 0
                decreaseButton.isEnabled = habit.habitcurrentCount > 0
            }
        }



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

        //NEW

        //NEW

        val addToArchivedHabit: Button = itemView.findViewById(R.id.buttonDone)
        addToArchivedHabit.tag = getItem(position)  // Set the product as the tag of the button

        addToArchivedHabit.setOnClickListener {
            val selectedHabit = it.tag as Habit  // Retrieve the product from the button's tag
            addToArchivedHabit(selectedHabit)

        }

        return itemView
    }
    fun addToArchivedHabit(habit: Habit) {
        val existingCartItem = dbHelper.getarchivedHabitByHabitId(habit.habitId)

        addHabitToArchiveListener?.invoke(habit)
        val toast = Toast.makeText(context, "Habit added to archive successfully!", Toast.LENGTH_LONG)
        toast.show()
         notifyDataSetChanged()
    }

    /*  fun addToArchivedHabit(habit: Habit) {
        val existingCartItem = dbHelper.getarchivedHabitByHabitId(habit.habitId)

            addHabitToArchiveListener?.invoke(habit)
            val toast = Toast.makeText(context, "Habit added to archive successfully!", Toast.LENGTH_LONG)
            toast.show()
            // notifyDataSetChanged()


    }*/
    fun setOnAddHabitToArchiveListener(listener: (Habit) -> Unit) {
        addHabitToArchiveListener = listener
    }

}