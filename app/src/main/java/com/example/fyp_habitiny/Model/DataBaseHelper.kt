package com.example.fyp_habitiny.Model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.fyp_habitiny.Model.User

/* Database Config*/
private val DataBaseName = "FYPDB.db"
private val ver : Int = 1

class DataBaseHelper (context: Context) : SQLiteOpenHelper(context,DataBaseName,null ,ver) {

    /* Customer Table */
    private val UserTableName = "TUser"

    private val User_Column_ID = "UserId"
    private val User_Column_FullName = "UserFullName"
    private val User_Column_Email = "UserEmail"
    private val User_Column_PhoneNo = "UserPhoneNo"
    private val User_Column_UserName = "UserUserName"
    private val User_Column_Password = "UserPassword"
    private val User_Column_IsActive = "UserIsActive"




    /* Admin Table */
    private val AdminTableName = "TAdmin"

    private val Admin_Column_ID = "AdminId"
    private val Admin_Column_FullName = "AdminFullName"
    private val Admin_Column_Email = "AdminEmail"
    private val Admin_Column_UserName = "AdminUserName"
    private val Admin_Column_Password = "AdminPassword"


    /* Habit Table */
    private val HabitTableName = "THabit"

    private val Habit_Column_ID = "HabitId"
    private val Habit_Column_UserId = "UserId"
    private val Habit_Column_Name = "HabitName"
    private val Habit_Column_StartDate = "StartDate"
    private val Habit_Column_HabitStatus = "HabitStatus"
    private val Habit_Column_Target = "Target"
    private val Habit_Column_TimePreference = "TimePreference"


    /*Recomanded habits table*/

    private val RecoHabitTableName = "TRecoHabit"

    private val RecoHabit_Column_ID = "RecoHabitId"
    private val RecoHabit_Column_RecoHabitName = "RecoHabitName"


    /*Moto table*/

    /* Recomanded habit Table */
    private val MotoTableName = "TMoto"

    private val Moto_Column_ID = "MotoId"
    private val Moto_Column_MotoText = "MotoText"



    /*feedback table*/

    private val FeedbackTableName = "TFeedback"

    private val Feedback_Column_ID = "FeedbackId"
    private val Feedback_Column_UserId = "UserId"
    private val Feedback_Column_FeedbackContent = "FeedbackContent"


    /*************************/

    // This is called the first time a database is accessed
    // Create a new database
    override fun onCreate(db: SQLiteDatabase?) {
        // Create user table
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + UserTableName + "(" + User_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  User_Column_FullName + " TEXT NOT NULL, " +
                    User_Column_Email + " TEXT NOT NULL, " + User_Column_PhoneNo + " TEXT NOT NULL, "  + User_Column_UserName + " TEXT NOT NULL, " +
                    User_Column_Password + " TEXT NOT NULL, " + User_Column_IsActive + " INTEGER NOT NULL )"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}
        //..............................................................
        // Create Admin table
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + AdminTableName + "(" + Admin_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Admin_Column_FullName + " TEXT NOT NULL, " +
                    Admin_Column_Email + " TEXT NOT NULL, " + Admin_Column_UserName + " TEXT NOT NULL, "  + Admin_Column_Password + " TEXT NOT NULL)"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}


        //..........................................................
        //Create habit table

       /* try {
            var sqlCreateStatement: String = "CREATE TABLE " + HabitTableName + "(" + Habit_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Habit_Column_UserId + "  INTEGER NOT NULL, " +
                    Habit_Column_Name + " TEXT NOT NULL, " + Habit_Column_StartDate+ " TEXT NOT NULL, "  + Habit_Column_HabitStatus+ " INTEGER NOT NULL)"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}*/

        //................................
        //CREATE HABIT TABLE

        try {
            var sqlCreateStatement: String = "CREATE TABLE " + HabitTableName + "(" + Habit_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Habit_Column_UserId + "  INTEGER, " +
                    Habit_Column_Name + " TEXT NOT NULL, " + Habit_Column_StartDate+ " TEXT NOT NULL, "  + Habit_Column_HabitStatus+ " INTEGER NOT NULL" + Habit_Column_Target+ " INTEGER NOT NULL" + Habit_Column_TimePreference+ " TEXT NOT NULL )"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}

        //.................................
        //Create RecoHabit Table
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + RecoHabitTableName + "(" + RecoHabit_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +  RecoHabit_Column_RecoHabitName + "  TEXT NOT NULL)"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}
        //...........................

        //Create Moto Table
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + MotoTableName + "(" + Moto_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Moto_Column_MotoText+ "  TEXT NOT NULL)"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}
        //...............................
        // Create Feedback table
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + FeedbackTableName + "(" + Feedback_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Feedback_Column_UserId + " INTEGER NOT NULL, " +
                    Feedback_Column_FeedbackContent + " TEXT NOT NULL)"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}

        //...........................
    }

        //.................................................................



    // This is called if the database ver. is changed
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    /**
     * return  1 : the new use has been add to the database successfully
     * return -1 : Error, adding new user
     * return -2 : can not Open/Create database
     * return -3 : user name is already exist
     *
     */
    fun addUser(user: User) : Int {

        val db: SQLiteDatabase
        val isUserNameAlreadyExists = checkUserName(user) // check if the username is already exist in the database
        if(isUserNameAlreadyExists < 0)
            return isUserNameAlreadyExists

        try {
            db = this.writableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val cv: ContentValues = ContentValues()

        cv.put(User_Column_FullName, user.userFullName)
        cv.put(User_Column_Email, user.userEmail)
        cv.put(User_Column_PhoneNo, user.userPhoneNo)
        cv.put(User_Column_UserName, user.userUserName.lowercase())
        cv.put(User_Column_Password, user.userPassword)
        cv.put(User_Column_IsActive, user.userIsActive)

        val success  =  db.insert(UserTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new user
        else return success.toInt() //1
    }
    fun addHabit(habit: Habit): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val cv: ContentValues = ContentValues()

        cv.put(Habit_Column_Name, habit.habitName)
        cv.put(Habit_Column_StartDate, habit.habitStartDate)
        cv.put(Habit_Column_HabitStatus, habit.habitStatus)
        cv.put(Habit_Column_Target, habit.habittarget)
        cv.put(Habit_Column_TimePreference, habit.habittimePreference)


        val success  =  db.insert(HabitTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new habit
        else return success.toInt() //1

    }
  /* fun addHabit(habit: Habit): Long {
       val db: SQLiteDatabase
       try {
           db = this.writableDatabase
       } catch(e: SQLiteException) {
           e.printStackTrace()
           return -2L // Indicate database access error
       }

       val cv = ContentValues()
       cv.put(Habit_Column_Name, habit.habitName)
       cv.put(Habit_Column_StartDate, habit.habitStartDate)

       val rowId: Long
       try {
           rowId = db.insert(HabitTableName, null, cv)
       } catch (e: SQLiteException) {
           e.printStackTrace()
           db.close()
           return -3L // Indicate error during insert
       } finally {
           db.close()
       }

       return rowId // Directly return the row ID of the newly inserted habit or -1 if error occurred
   }*/


    private fun checkUserName(user: User): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = user.userUserName.lowercase()

        val sqlStatement = "SELECT * FROM $UserTableName WHERE $User_Column_UserName = ?"
        val param = arrayOf(userName)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)

        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3 // error the user name is already exist
        }

        cursor.close()
        db.close()
        return 0 //User not found

    }

    fun getUser(user: User) : Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val userName = user.userUserName.lowercase()
        val userPassword = user.userPassword
        //val sqlStatement = "SELECT * FROM $TableName WHERE $Column_UserName = $userName AND $Column_Password = $userPassword"

        val sqlStatement = "SELECT * FROM $UserTableName WHERE $User_Column_UserName = ? AND $User_Column_Password = ?"
        val param = arrayOf(userName,userPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -1 //User not found

    }

  /*  @SuppressLint("Range")
    fun getHabit(): List<Habit> {
        val productList = mutableListOf<Habit>()
        val db: SQLiteDatabase

        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            // Handle the exception as needed
            return emptyList()
        }

        val sqlStatement = "SELECT * FROM $HabitTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        while (cursor.moveToNext()) {
            val HabitName = cursor.getString(cursor.getColumnIndex(Habit_Column_Name))
            val HabitStartDate = cursor.getString(cursor.getColumnIndex(Habit_Column_StartDate))


            val product = Habit(-1,0, HabitName, HabitStartDate, 0)
            productList.add(product)
        }

        cursor.close()
        db.close()

        return productList
    }*/
  @SuppressLint("Range")
  fun getHabit(): List<Habit> {
      val productList = mutableListOf<Habit>()
      val db: SQLiteDatabase

      try {
          db = this.readableDatabase
      } catch (e: SQLiteException) {
          // Handle the exception as needed
          return emptyList()
      }

      val sqlStatement = "SELECT * FROM $HabitTableName"
      val cursor: Cursor = db.rawQuery(sqlStatement, null)

      while (cursor.moveToNext()) {
          val id = cursor.getInt(cursor.getColumnIndex(Habit_Column_ID)) // Retrieve the habit ID
          val habitName = cursor.getString(cursor.getColumnIndex(Habit_Column_Name))
          val habitStartDate = cursor.getString(cursor.getColumnIndex(Habit_Column_StartDate))
          val habitStatus = cursor.getInt(cursor.getColumnIndex(Habit_Column_HabitStatus))
          val habitTarget = cursor.getInt(cursor.getColumnIndex(Habit_Column_Target))
          val habitTimePre = cursor.getString(cursor.getColumnIndex(Habit_Column_TimePreference))


          val habit = Habit(id, 0, habitName, habitStartDate, habitStatus, habitTarget, habitTimePre) // Use the actual ID
          productList.add(habit)
      }

      cursor.close()
      db.close()

      return productList
  }

    fun deleteHabit(habitId: Int): Boolean {
        val db = this.writableDatabase
        val selection = "$Habit_Column_ID = ?" // Use = for an exact match
        val selectionArgs = arrayOf(habitId.toString())
        val deletedRows = db.delete(HabitTableName, selection, selectionArgs)
        db.close()
        return deletedRows > 0
    }
    fun updateHabitStatus(habitId: Int, status: Int): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        } catch(e: SQLiteException) {
            e.printStackTrace()
            return -2 // Indicate database access error
        }

        val cv = ContentValues()
        cv.put(Habit_Column_HabitStatus, status)

        val selection = "$Habit_Column_ID = ?"
        val selectionArgs = arrayOf(habitId.toString())

        val count = db.update(HabitTableName, cv, selection, selectionArgs)
        db.close()
        return count // The number of rows affected
    }


}