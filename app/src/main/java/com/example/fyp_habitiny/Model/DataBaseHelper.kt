package com.example.fyp_habitiny.Model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList

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
   // private val User_Column_IsActive = "UserIsActive"




    /* Admin Table */
    private val AdminTableName = "TAdmin"

    private val Admin_Column_ID = "AdminId"
    private val Admin_Column_FullName = "AdminFullName"
    private val Admin_Column_Email = "AdminEmail"
    private val Admin_Column_UserName = "AdminUserName"
    private val Admin_Column_Password = "AdminPassword"
    private val Admin_Column_PhoneNo = "AdminPhoneNo"


    /* Habit Table */
    private val HabitTableName = "THabit"

    private val Habit_Column_ID = "HabitId"
    private val Habit_Column_UserId = "UserId"
    private val Habit_Column_Name = "HabitName"
    private val Habit_Column_StartDate = "StartDate"
    private val Habit_Column_HabitStatus = "HabitStatus"
    private val Habit_Column_Target = "Target"
    private val Habit_Column_TimePreference = "TimePreference"
    private val Habit_Column_CurrentTargetCount = "CurrentTargetCount"



    /* Archive Habit Table */
    private val ArchiveHabitTableName = "TArchiveHabit"

    private val Archive_Habit_Column_ID = "ArchiveHabitId"
    private val Archive_Habit_Column_UserId = "ArchiveUserId"
    private val Archive_Habit_Column_HabitId = "HabitId"
    private val Archive_Habit_Column_Name = "ArchiveHabitName"
    private val Archive_Habit_Column_StartDate = "ArchiveStartDate"
  //  private val Archive_Habit_Column_EndtDate = "ArchiveEndDate"
    private val Archive_Habit_Column_Target = "ArchiveTarget"
    private val Archive_Habit_Column_TimePreference = "ArchiveTimePreference"
    private val Archive_Habit_Column_CurrentTargetCount = "ArchiveCurrentTargetCount"


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
   // private val Feedback_Column_UserId = "UserId"
    private val Feedback_Column_FeedbackContent = "FeedbackContent"
    private val Feedback_Column_Rating= "FeedbackRating"


    /*************************/

    // This is called the first time a database is accessed
    // Create a new database
    override fun onCreate(db: SQLiteDatabase?) {
        // Create user table
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + UserTableName + "(" + User_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  User_Column_FullName + " TEXT NOT NULL, " +
                    User_Column_Email + " TEXT NOT NULL, " + User_Column_PhoneNo + " TEXT NOT NULL, "  + User_Column_UserName + " TEXT NOT NULL, " +
                    User_Column_Password + " TEXT NOT NULL)"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}
        //..............................................................
        // Create Admin table
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + AdminTableName + "(" + Admin_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Admin_Column_FullName + " TEXT NOT NULL, " +
                    Admin_Column_Email + " TEXT NOT NULL, " + Admin_Column_UserName + " TEXT NOT NULL, "  + Admin_Column_Password + " TEXT NOT NULL" + Admin_Column_PhoneNo + "TEXT NOT NULL)"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}


        //..........................................................


        //................................
        //CREATE HABIT TABLE

        try {
            var sqlCreateStatement: String = "CREATE TABLE " + HabitTableName + "(" + Habit_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Habit_Column_UserId + "  INTEGER, " +
                    Habit_Column_Name + " TEXT NOT NULL, " + Habit_Column_StartDate+ " TEXT NOT NULL, "  + Habit_Column_HabitStatus+ " INTEGER NOT NULL" +
                    Habit_Column_Target+ " INTEGER NOT NULL" + Habit_Column_TimePreference+ " TEXT NOT NULL" + Habit_Column_CurrentTargetCount + "INTEGER )"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}

        //................................
        //CREATE Archive HABIT TABLE

        try {
            var sqlCreateStatement: String = "CREATE TABLE " + ArchiveHabitTableName + "(" + Archive_Habit_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Archive_Habit_Column_HabitId + "  INTEGER, " +  Archive_Habit_Column_UserId + "  INTEGER, " +
                    Archive_Habit_Column_Name + " TEXT NOT NULL, " + Archive_Habit_Column_StartDate+ " TEXT NOT NULL, " +  Archive_Habit_Column_Target+ " INTEGER NOT NULL" + Archive_Habit_Column_TimePreference+ " TEXT NOT NULL" +
                    Archive_Habit_Column_CurrentTargetCount + "INTEGER)"

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
            var sqlCreateStatement: String = "CREATE TABLE " + FeedbackTableName + "(" + Feedback_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Feedback_Column_FeedbackContent + " TEXT NOT NULL, "+ Feedback_Column_Rating +" INTEGER NOT NULL)"

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
      //  cv.put(User_Column_IsActive, user.userIsActive)

        val success  =  db.insert(UserTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new user
        else return success.toInt() //1
    }
    fun addAdmimnUser(admin: Admin) : Int {

        val db: SQLiteDatabase
        val isAdminUserNameAlreadyExists = checkAdminUserName(admin) // check if the username is already exist in the database
        if(isAdminUserNameAlreadyExists < 0)
            return isAdminUserNameAlreadyExists

        try {
            db = this.writableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val cv: ContentValues = ContentValues()

        cv.put(Admin_Column_FullName, admin.adminFullName)
        cv.put(Admin_Column_Email, admin.adminEmail)
        cv.put(Admin_Column_PhoneNo, admin.adminPhoneNo)
        cv.put(Admin_Column_UserName, admin.adminUserName.lowercase())
        cv.put(Admin_Column_Password, admin.adminPassword)

        val success  =  db.insert(AdminTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new user
        else return success.toInt() //1
    }
    fun addFeedback(feedback: Feedback): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        } catch (e: SQLiteException) {
            return -2
        }

        val cv: ContentValues = ContentValues()

        // Use Feedback_Column_FeedbackContent instead of FeedbackTableName
        cv.put(Feedback_Column_FeedbackContent, feedback.feedbackText)
        cv.put(Feedback_Column_Rating, feedback.feedbackRating)

        val success = db.insert(FeedbackTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() // Error, adding new feedback
        else return success.toInt() // 1
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
    private fun checkAdminUserName(admin: Admin): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val AdminuserName = admin.adminUserName.lowercase()

        val sqlStatement = "SELECT * FROM $AdminTableName WHERE $Admin_Column_UserName = ?"
        val param = arrayOf(AdminuserName)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)

        if(cursor.moveToFirst()){
            // The Adminuser is found
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
    fun GetUserDetails(user: User) : Int {

        val db: SQLiteDatabase
        val isUserNameAlreadyExists = checkUserName(user) // check if the username exists in the database
        if(isUserNameAlreadyExists < 0)
            return isUserNameAlreadyExists


        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }
        val userFullName = user.userFullName
        val userEmail = user.userEmail
        val userPhoneNo = user.userPhoneNo
        val userName = user.userUserName.lowercase()
        val userPassword = user.userPassword



        //val sqlStatement = "SELECT * FROM $TableName WHERE $Column_UserName = $userName AND $Column_Password = $userPassword"

        val sqlStatement = "SELECT * FROM $UserTableName WHERE $User_Column_UserName = ? AND $User_Column_Password = ?" +
                " AND $User_Column_FullName = ? AND $User_Column_Email = ? AND $User_Column_PhoneNo = ?"
        val param = arrayOf(userName,userPassword, userEmail, userPhoneNo, userFullName)
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
    fun getAdminUser(admin: Admin) : Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val AdminuserName = admin.adminUserName.lowercase()
        val AdminuserPassword = admin.adminPassword
        //val sqlStatement = "SELECT * FROM $TableName WHERE $Column_UserName = $userName AND $Column_Password = $userPassword"

        val sqlStatement = "SELECT * FROM $AdminTableName WHERE $Admin_Column_UserName = ? AND $Admin_Column_Password = ?"
        val param = arrayOf(AdminuserName,AdminuserPassword)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)
        if(cursor.moveToFirst()){
            // The admin user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -1 //User not found

    }
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
          val habitCurrentCount = cursor.getInt(cursor.getColumnIndex(Habit_Column_CurrentTargetCount))


          val habit = Habit(id, 0, habitName, habitStartDate, habitStatus, habitTarget, habitTimePre, habitCurrentCount) // Use the actual ID
          productList.add(habit)
      }

      cursor.close()
      db.close()

      return productList
  }
    @SuppressLint("Range")
    fun getPreCraetedHabits(): List<RecoHabit> {
        val productList = mutableListOf<RecoHabit>()
        val db: SQLiteDatabase

        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            // Handle the exception as needed
            return emptyList()
        }

        val sqlStatement = "SELECT * FROM $RecoHabitTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(RecoHabit_Column_ID)) // Retrieve the habit ID
            val habitName = cursor.getString(cursor.getColumnIndex(RecoHabit_Column_RecoHabitName))


            val Recohabit = RecoHabit(id, habitName)
            productList.add(Recohabit)
        }

        cursor.close()
        db.close()

        return productList
    }

   @SuppressLint("Range")
   fun getMototext(): List<Moto> {
       val productList = mutableListOf<Moto>()

       try {
           this.readableDatabase.use { db ->
               val cursor = db.rawQuery("SELECT * FROM $MotoTableName", null)
               cursor.use {
                   while (it.moveToNext()) {
                       val id = it.getInt(it.getColumnIndex(Moto_Column_ID))
                       val motoText = it.getString(it.getColumnIndex(Moto_Column_MotoText))
                       productList.add(Moto(id, motoText))
                   }
               }
           }
       } catch (e: SQLiteException) {
           // Log the exception or handle it as needed
           return emptyList()
       }

       return productList
   }

    @SuppressLint("Range")
    fun getFeedback(): List<Feedback> {
        val productList = mutableListOf<Feedback>()

        try {
            this.readableDatabase.use { db ->
                val cursor = db.rawQuery("SELECT * FROM $FeedbackTableName", null)
                cursor.use {
                    while (it.moveToNext()) {
                        val id = it.getInt(it.getColumnIndex(Feedback_Column_ID))
                        val feedbackText = it.getString(it.getColumnIndex(Feedback_Column_FeedbackContent))
                        val rating = it.getDouble((it.getColumnIndex(Feedback_Column_Rating)))
                        productList.add(Feedback(id, feedbackText, rating))
                    }
                }
            }
        } catch (e: SQLiteException) {
            // Log the exception or handle it as needed
            return emptyList()
        }

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
    fun deleteUser(userName: String): Boolean {
        val db = this.writableDatabase
        val selection = "$User_Column_UserName = ?"
        val selectionArgs = arrayOf(userName)
        Log.d("DeleteUserQuery", "DELETE FROM $UserTableName WHERE $selection")
        val deletedRows = db.delete(UserTableName, selection, selectionArgs)
        Log.d("DeletedRows", deletedRows.toString())
        db.close()
        return deletedRows > 0
    }

    /* fun deleteUser(userName: String): Boolean {
        val db = this.writableDatabase
        val selection = "$User_Column_UserName = ?"
        val selectionArgs = arrayOf(userName.toString())
        val deletedRows = db.delete(UserTableName, selection, selectionArgs)
        db.close()
        return deletedRows > 0
    }*/
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
    fun updateHabitProgress(habitId: Int, newProgress: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(Habit_Column_CurrentTargetCount, newProgress)
        }
        val selection = "$Habit_Column_ID = ?"
        val selectionArgs = arrayOf(habitId.toString())
        db.update(HabitTableName, contentValues, selection, selectionArgs)
        db.close()
    }
    //update user details
    fun updateUserDetails(user: User): Int {
        return try {
            val db = this.writableDatabase

            val values = ContentValues().apply {
                put(User_Column_FullName, user.userFullName)
                put(User_Column_Email, user.userEmail)
                put(User_Column_PhoneNo, user.userPhoneNo)
                put(User_Column_UserName, user.userUserName)
            }

            val whereClause = "$User_Column_UserName = ?"  // Update based on username
            val whereArgs = arrayOf(user.userUserName)

            val updatedRows = db.update(UserTableName, values, whereClause, whereArgs)

            db.close()
            updatedRows // Success, return the number of updated rows

        } catch (e: Exception) {
            e.printStackTrace()
            -1 // Error updating user details
        }
    }
//get user details by username
    @SuppressLint("Range")
    fun getUserDetailsByUsername(username: String): User? {
        val db = this.readableDatabase

        val sqlStatement = "SELECT * FROM $UserTableName WHERE $User_Column_UserName = ?"
        val param = arrayOf(username)
        val cursor: Cursor = db.rawQuery(sqlStatement, param)

        return if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndex(User_Column_ID))
            val fullName = cursor.getString(cursor.getColumnIndex(User_Column_FullName))
            val userEmail = cursor.getString(cursor.getColumnIndex(User_Column_Email))
            val userPhoneNo = cursor.getString(cursor.getColumnIndex(User_Column_PhoneNo))
            val userPassword = cursor.getString(cursor.getColumnIndex(User_Column_Password))

            cursor.close()
            db.close()

            User(userId, fullName, userEmail, userPhoneNo, username, userPassword)
        } else {
            cursor.close()
            db.close()
            null // User not found
        }
    }
   /* fun addHabitToArchive(archiveHabit: ArchiveHabit): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch(e: SQLiteException) {
            Log.e("DataBaseHelper", "Error opening writable database: ${e.message}")
            return -2
        }
        val cv: ContentValues = ContentValues()

        cv.put(Archive_Habit_Column_Name, archiveHabit.archivedHabitName)
        cv.put(Archive_Habit_Column_Target, archiveHabit.archivedHabittarget)
        cv.put(Archive_Habit_Column_StartDate, archiveHabit.archivedHabitStartDate)
        cv.put(Archive_Habit_Column_EndtDate, archiveHabit.archivedHabitEndDate)
        cv.put(Archive_Habit_Column_CurrentTargetCount, archiveHabit.archivedHabitcurrentCount)
        cv.put(Archive_Habit_Column_TimePreference, archiveHabit.archivedHabittimePreference)

        //continue here
        val success  =  db.insert(ArchiveHabitTableName, null, cv)

        db.close()
        if (success.toInt() == -1) {
            Log.e("DataBaseHelper", "addItemToCart: Error adding cart item to database.")

            return success.toInt() //Error, adding new user
        }
        else {
            Log.d("DataBaseHelper", "addItemToCart: Successfully added cart item to database.")
            return success.toInt() //1
        }
    }*/
   @SuppressLint("Range")
   fun getAllArchivedHabits(): List<ArchiveHabit> {
       val archivedHabitList = mutableListOf<ArchiveHabit>()
       val db: SQLiteDatabase

       try {
           db = this.readableDatabase
       } catch (e: SQLiteException) {
           // Handle the exception as needed
           Log.e("DataBaseHelper", "Error opening readable database: ${e.message}")
           return emptyList()
       }

       val sqlStatement = "SELECT * FROM $ArchiveHabitTableName"
       val cursor: Cursor = db.rawQuery(sqlStatement, null)

       while (cursor.moveToNext()) {

           //

           val HabitName = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_Name))
           val StartDate = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_StartDate))
           val Target = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_Target))
           val PrefTime = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_TimePreference))
           val CurrentCount = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_CurrentTargetCount))

           val cart = ArchiveHabit(-1, 0, 0,  HabitName,StartDate, Target, PrefTime, CurrentCount )
           archivedHabitList.add(cart)
       }

       cursor.close()
       db.close()
       Log.d("DataBaseHelper", "getAllArchivedHabits: Successfully retrieved ${archivedHabitList.size} cart items.")

       return archivedHabitList
   }
   @SuppressLint("Range")
   fun getarchivedHabitByHabitId(habitId: Int): ArchiveHabit? {//needs modifications
       val db = this.readableDatabase
       var archviedhHabit: ArchiveHabit? = null

       val cursor = db.query(
           ArchiveHabitTableName, // Table name
           arrayOf(Archive_Habit_Column_ID,Archive_Habit_Column_UserId,  Archive_Habit_Column_Name, Archive_Habit_Column_StartDate,
               Archive_Habit_Column_Target, Archive_Habit_Column_TimePreference, Archive_Habit_Column_CurrentTargetCount), // Columns to return
           "$Archive_Habit_Column_HabitId = ?", // Selection criteria
           arrayOf(habitId.toString()), // Selection arguments
           null, // Group by
           null, // Having
           null // Order by
       )

       if (cursor.moveToFirst()) {
           val archivedHabitId = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_ID))
           val userId = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_UserId))
           val habitName = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_Name))
           val startDate = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_StartDate))
           val target = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_Target))
           val prefTime = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_TimePreference))
           val currentCount = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_CurrentTargetCount))

           archviedhHabit = ArchiveHabit(archivedHabitId , userId, habitId, habitName , startDate, target, prefTime, currentCount)

       }

       cursor.close()
       db.close()
       return archviedhHabit
   }
    fun addHabitToArchive(archiveHabit: ArchiveHabit): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        }
        catch(e: SQLiteException) {
            Log.e("DataBaseHelper", "Error opening writable database: ${e.message}")
            return -2
        }

        val cv: ContentValues = ContentValues()

        cv.put(Archive_Habit_Column_Name, archiveHabit.archivedHabitName)
        cv.put(Archive_Habit_Column_StartDate, archiveHabit.archivedHabitStartDate)
        cv.put(Archive_Habit_Column_Target,archiveHabit.archivedHabittarget)
        cv.put(Archive_Habit_Column_TimePreference, archiveHabit.archivedHabittimePreference)
        cv.put(Archive_Habit_Column_CurrentTargetCount, archiveHabit.archivedHabitcurrentCount)




        val success  =  db.insert(ArchiveHabitTableName, null, cv)

        db.close()
        if (success.toInt() == -1) {
            Log.e("DataBaseHelper", "addIHabitToArchive: Error adding habit to archive.")

            return success.toInt() //Error, adding new user
        }
        else {
            Log.d("DataBaseHelper", "addIHabitToArchive: Successfully added adding habit to archive.")
            return success.toInt() //1
        }

    }

   /* fun moveHabitToArchive(archiveHabit: ArchiveHabit): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        } catch (e: SQLiteException) {
            Log.e("DataBaseHelper", "Error opening writable database: ${e.message}")
            return -2
        }

        db.beginTransaction()
        try {
            // Insert the habit into the archive table
            val cv: ContentValues = ContentValues()
            cv.put(Archive_Habit_Column_Name, archiveHabit.archivedHabitName)
            cv.put(Archive_Habit_Column_Target, archiveHabit.archivedHabittarget)
            cv.put(Archive_Habit_Column_StartDate, archiveHabit.archivedHabitStartDate)
            cv.put(Archive_Habit_Column_CurrentTargetCount, archiveHabit.archivedHabitcurrentCount)
            cv.put(Archive_Habit_Column_TimePreference, archiveHabit.archivedHabittimePreference)

            val archiveSuccess = db.insert(ArchiveHabitTableName, null, cv)

            if (archiveSuccess == -1L) {
                db.endTransaction()
                Log.e("DataBaseHelper", "Error adding habit to archive table.")
                return -1
            }

            // Delete the habit from the original table
            val deleteSuccess = db.delete(
                HabitTableName,
                "$Habit_Column_ID=?",
                arrayOf(archiveHabit.archivedHabitId.toString())
            )

            if (deleteSuccess == 0) {
                db.endTransaction()
                Log.e("DataBaseHelper", "Error deleting habit from original table.")
                return -1
            }

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }

        return 1
    }*/
    fun moveHabitToArchive(archiveHabit: ArchiveHabit): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        } catch (e: SQLiteException) {
            Log.e("DataBaseHelper", "Error opening writable database: ${e.message}")
            return -2
        }

        db.beginTransaction()
        try {
            // Insert the habit into the archive table
            val cv: ContentValues = ContentValues()

            cv.put(Archive_Habit_Column_Name, archiveHabit.archivedHabitName)
            cv.put(Archive_Habit_Column_Target, archiveHabit.archivedHabittarget)
            cv.put(Archive_Habit_Column_StartDate, archiveHabit.archivedHabitStartDate)
            cv.put(Archive_Habit_Column_CurrentTargetCount, archiveHabit.archivedHabitcurrentCount)
            cv.put(Archive_Habit_Column_TimePreference, archiveHabit.archivedHabittimePreference)

            val archiveSuccess = db.insert(ArchiveHabitTableName, null, cv)
            if (archiveSuccess == -1L) {
                Log.e("DataBaseHelper", "Error adding habit to archive table.")
                return -1 // Transaction will be ended in finally block
            }

            // Delete the habit from the original table
            val deleteSuccess = db.delete(HabitTableName, "$Habit_Column_ID=?", arrayOf(archiveHabit.archivedHabitId.toString()))
            if (deleteSuccess == 0) {
                Log.e("DataBaseHelper", "Error deleting habit from original table.")
                return -1 // Transaction will be ended in finally block
            }

            db.setTransactionSuccessful() // Marks this transaction as successful
        } finally {
            db.endTransaction() // Ends the transaction
            db.close() // Close the database
        }

        return 1 // Success
    }




}