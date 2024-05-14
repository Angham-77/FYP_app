package com.example.fyp_habitiny.Model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.time.LocalDate
import java.time.ZoneId
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
    private val Habit_Column_EndDate = "EndDate"
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
    private val Archive_Habit_Column_EndtDate = "ArchiveEndDate"
    private val Archive_Habit_Column_Target = "ArchiveTarget"
    private val Archive_Habit_Column_TimePreference = "ArchiveTimePreference"
    private val Archive_Habit_Column_CurrentTargetCount = "ArchiveCurrentTargetCount"


    /*Recomanded habits table*/

    private val RecoHabitTableName = "TRecoHabit"

    private val RecoHabit_Column_ID = "RecoHabitId"
    private val RecoHabit_Column_RecoHabitName = "RecoHabitName"
    private val RecoHabit_Column_AdminId = "RecoAdminId"


    /*Moto table*/

    /* Recomanded habit Table */
    private val MotoTableName = "TMoto"

    private val Moto_Column_ID = "MotoId"
    private val Moto_Column_UserID = "MotoUserId"
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
            var sqlCreateStatement: String = "CREATE TABLE " + HabitTableName + "(" + Habit_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Habit_Column_UserId + "  INTEGER NOT NULL, " +
                    Habit_Column_Name + " TEXT NOT NULL, " + Habit_Column_StartDate+ " TEXT NOT NULL, " + Habit_Column_EndDate+ " TEXT NOT NULL, " + Habit_Column_HabitStatus+ " INTEGER NOT NULL" +
                    Habit_Column_Target+ " INTEGER NOT NULL" + Habit_Column_TimePreference+ " TEXT NOT NULL" + Habit_Column_CurrentTargetCount + "INTEGER," +"FOREIGN KEY (" + Habit_Column_UserId + ") REFERENCES TUser(UserId))"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}

        //................................
        //CREATE Archive HABIT TABLE

        try {
            var sqlCreateStatement: String = "CREATE TABLE " + ArchiveHabitTableName + "(" + Archive_Habit_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  Archive_Habit_Column_HabitId + "  INTEGER NOT NULL, " +  Archive_Habit_Column_UserId + "  INTEGER NOT NULL, " +
                    Archive_Habit_Column_Name + " TEXT NOT NULL, " + Archive_Habit_Column_StartDate+ " TEXT NOT NULL, " + Archive_Habit_Column_EndtDate+ " TEXT NOT NULL, "+  Archive_Habit_Column_Target+ " INTEGER NOT NULL" + Archive_Habit_Column_TimePreference+ " TEXT NOT NULL" +
                    Archive_Habit_Column_CurrentTargetCount + "INTEGER,  " +  "FOREIGN KEY (" + Archive_Habit_Column_HabitId + ") REFERENCES " + HabitTableName + "(" + Habit_Column_ID + "), " +
            "FOREIGN KEY (" + Archive_Habit_Column_UserId + ") REFERENCES TUser(UserId))"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}

        //.................................
        //Create RecoHabit Table
        try {
            var sqlCreateStatement: String = "CREATE TABLE " + RecoHabitTableName + "(" + RecoHabit_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RecoHabit_Column_RecoHabitName + "  TEXT NOT NULL"  + RecoHabit_Column_AdminId + "INTEGER, " + "FOREIGN KEY (" + RecoHabit_Column_AdminId + ") REFERENCES " +
                    AdminTableName + "(" + Admin_Column_ID + "))"

            db?.execSQL(sqlCreateStatement)
        }
        catch (e: SQLiteException) {}
        //...........................


        //Create Moto Table
        try {
           /* var sqlCreateStatement: String = "CREATE TABLE " + MotoTableName + "(" + Moto_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Moto_Column_MotoText+ "  TEXT NOT NULL, "+ Moto_Column_UserID + "INTEGER, " + "FOREIGN KEY (" + Moto_Column_UserID + ") REFERENCES " +
                    UserTableName + "(" + User_Column_ID + "))"*/

            //
            var sqlCreateStatement: String = "CREATE TABLE " + MotoTableName + "(" +
                    Moto_Column_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Moto_Column_MotoText+ " TEXT NOT NULL, " +
                    Moto_Column_UserID + " INTEGER, " +
                    "FOREIGN KEY (" + Moto_Column_UserID + ") REFERENCES " +
                    UserTableName + "(" + User_Column_ID + "))"

            //

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
    fun addMoto(moto: Moto): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        } catch (e: SQLiteException) {
            return -2
        }

        val cv: ContentValues = ContentValues()


        cv.put(Moto_Column_MotoText, moto.motoText)
        cv.put(Moto_Column_UserID, moto.motoUserid)


        val success = db.insert(MotoTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() // Error, adding new feedback
        else return success.toInt() // 1
    }
    @SuppressLint("Range")
    fun getRandomMoto(userId: Int?): Moto? {
        val db: SQLiteDatabase = this.readableDatabase
        var moto: Moto? = null
        val query = if (userId != null) {
            "SELECT * FROM $MotoTableName WHERE $Moto_Column_UserID = ? OR $Moto_Column_UserID IS NULL ORDER BY RANDOM() LIMIT 1"
        } else {
            "SELECT * FROM $MotoTableName ORDER BY RANDOM() LIMIT 1"
        }
        val cursor: Cursor = db.rawQuery(query, userId?.let { arrayOf(it.toString()) })

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(Moto_Column_ID))
            val motoText = cursor.getString(cursor.getColumnIndex(Moto_Column_MotoText))
            val motoUserId = cursor.getInt(cursor.getColumnIndex(Moto_Column_UserID))
            moto = Moto(id, motoText, motoUserId)
        }

        cursor.close()
        db.close()
        return moto
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
        cv.put(Habit_Column_UserId, habit.HabitUserId)
        cv.put(Habit_Column_StartDate, habit.habitStartDate)
        cv.put(Habit_Column_EndDate, habit.habitEndtDate)
        cv.put(Habit_Column_HabitStatus, habit.habitStatus)
        cv.put(Habit_Column_Target, habit.habittarget)
        cv.put(Habit_Column_TimePreference, habit.habittimePreference)



        val success  =  db.insert(HabitTableName, null, cv)

        db.close()
        if (success.toInt() == -1) return success.toInt() //Error, adding new habit
        else return success.toInt() //1

    }
    fun addRecoHabit(recoHabit: RecoHabit): Int {
        val db: SQLiteDatabase
        val isRecoHabtAlreadyExists = checkRecoHabit(recoHabit)
        if(isRecoHabtAlreadyExists < 0)
            return isRecoHabtAlreadyExists

        try {
            db = this.writableDatabase
        }

        catch(e: SQLiteException) {
            return -2
        }

        val cv: ContentValues = ContentValues()

        cv.put(RecoHabit_Column_RecoHabitName, recoHabit.recoHabitName)
        cv.put(RecoHabit_Column_AdminId, recoHabit.recoHabitAdminId)

        val success  =  db.insert(RecoHabitTableName, null, cv)

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
    private fun checkRecoHabit(recoHabit: RecoHabit): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val newRecoHabit = recoHabit.recoHabitName.lowercase()

        val sqlStatement = "SELECT * FROM $RecoHabitTableName WHERE LOWER($RecoHabit_Column_RecoHabitName) = ?"
        val param = arrayOf(newRecoHabit)
        val cursor: Cursor = db.rawQuery(sqlStatement, param)

        if(cursor.moveToFirst()) {
            cursor.close()
            db.close()
            return -3 // Habit name already exists
        }

        cursor.close()
        db.close()
        return 0 // Habit name does not exist, okay to proceed
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
    //get habit by user id
    fun getHabit(userId: Int): List<Habit> {
        val habitList = mutableListOf<Habit>()
        val db: SQLiteDatabase

        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            Log.e("DataBaseHelper", "Error opening readable database: ${e.message}")
            return emptyList() // Handle the exception as needed
        }

        val cursor: Cursor = db.query(
            HabitTableName,
            null, // null selects all columns
            "$Habit_Column_UserId = ?", // WHERE clause
            arrayOf(userId.toString()), // WHERE clause arguments
            null, // groupBy
            null, // having
            null  // orderBy
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(Habit_Column_ID))
            val userId = cursor.getInt(cursor.getColumnIndex(Habit_Column_UserId))
            val habitName = cursor.getString(cursor.getColumnIndex(Habit_Column_Name))
            val habitStartDate = cursor.getString(cursor.getColumnIndex(Habit_Column_StartDate))
            val habitEndDate = cursor.getString(cursor.getColumnIndex(Habit_Column_EndDate))
            val habitStatus = cursor.getInt(cursor.getColumnIndex(Habit_Column_HabitStatus))
            val habitTarget = cursor.getInt(cursor.getColumnIndex(Habit_Column_Target))
            val habitTimePreference = cursor.getString(cursor.getColumnIndex(Habit_Column_TimePreference))
            val habitCurrentCount = cursor.getInt(cursor.getColumnIndex(Habit_Column_CurrentTargetCount))

            val habit = Habit(id, userId, habitName, habitStartDate, habitStatus, habitTarget, habitTimePreference, habitCurrentCount, habitEndDate)
            habitList.add(habit)
        }

        cursor.close()
        db.close()

        return habitList
    }
    @SuppressLint("Range")
    fun getArchivedHabitByUserId(userId: Int): List<ArchiveHabit> {
        val habitList = mutableListOf<ArchiveHabit>()
        val db: SQLiteDatabase

        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            Log.e("DataBaseHelper", "Error opening readable database: ${e.message}")
            return emptyList() // Handle the exception as needed
        }

        val cursor: Cursor = db.query(
            ArchiveHabitTableName,
            null, // null selects all columns
            "$Archive_Habit_Column_UserId = ?", // WHERE clause
            arrayOf(userId.toString()), // WHERE clause arguments
            null, // groupBy
            null, // having
            null  // orderBy
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_ID))
            val userId = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_UserId))
            val habitName = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_Name))
            val habitStartDate = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_StartDate))
            val habitEndtDate = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_EndtDate))
            val AhabitID = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_HabitId))
            val habitTarget = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_Target))
            val habitTimePreference = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_TimePreference))
            val habitCurrentCount = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_CurrentTargetCount))

            val archivedHabit = ArchiveHabit(id, userId,AhabitID,  habitName, habitStartDate, habitTarget, habitTimePreference, habitCurrentCount, habitEndtDate)
            habitList.add(archivedHabit)
        }

        cursor.close()
        db.close()

        return habitList
    }
   /* @SuppressLint("Range")
    fun getMotoByUserId(userId: Int): List<Moto> {
        val moto = mutableListOf<Moto>()
        val db: SQLiteDatabase

        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            Log.e("DataBaseHelper", "Error opening readable database: ${e.message}")
            return emptyList()
        }

        val cursor: Cursor = db.query(
            MotoTableName,
            null,
            "$Moto_Column_UserID = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(Moto_Column_ID))
            val motoText = cursor.getString(cursor.getColumnIndex(Moto_Column_MotoText))
            val motoUserId = cursor.getInt(cursor.getColumnIndex(Moto_Column_UserID))

            val setmotoText = Moto(id, motoText,  motoUserId)
            moto.add(setmotoText)
        }

        cursor.close()
        db.close()

        return moto
    }*/
   @SuppressLint("Range")
   fun getMotoByUserId(context: Context): List<Moto> {
       val userId = getCurrentUserId(context)
       val motoList = mutableListOf<Moto>()
       val db: SQLiteDatabase

       try {
           db = this.readableDatabase
       } catch (e: SQLiteException) {
           Log.e("DataBaseHelper", "Error opening readable database: ${e.message}")
           return emptyList()
       }

       val selectionArgs = if (userId == -1) arrayOf() else arrayOf(userId.toString())
       val query = "SELECT * FROM $MotoTableName WHERE $Moto_Column_UserID IS NULL" +
               if (userId != -1) " OR $Moto_Column_UserID = ?" else ""

       val cursor: Cursor = db.rawQuery(query, selectionArgs)

       while (cursor.moveToNext()) {
           val id = cursor.getInt(cursor.getColumnIndex(Moto_Column_ID))
           val motoText = cursor.getString(cursor.getColumnIndex(Moto_Column_MotoText))
           val motoUserId = cursor.getInt(cursor.getColumnIndex(Moto_Column_UserID))

           motoList.add(Moto(id, motoText, motoUserId))
       }

       cursor.close()
       db.close()

       return motoList
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
            val adminIdReco = cursor.getInt(cursor.getColumnIndex(RecoHabit_Column_AdminId))


            val Recohabit = RecoHabit(id, habitName, adminIdReco)
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
                       val userId = it.getInt(it.getColumnIndex(Moto_Column_UserID))
                       val motoText = it.getString(it.getColumnIndex(Moto_Column_MotoText))
                       productList.add(Moto(id, motoText,userId ))
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
    fun deleteRecoHabit(recoHabitId: Int): Boolean {
        val db = this.writableDatabase
        val selection = "$RecoHabit_Column_ID = ?" // Use = for an exact match
        val selectionArgs = arrayOf(recoHabitId.toString())
        val deletedRows = db.delete(RecoHabitTableName, selection, selectionArgs)
        db.close()
        return deletedRows > 0
    }
    fun deletearchivedHabit(archivedHabitId: Int): Boolean {
        val db = this.writableDatabase
        val selection = "$Archive_Habit_Column_ID = ?" // Use = for an exact match
        val selectionArgs = arrayOf(archivedHabitId.toString())
        val deletedRows = db.delete(ArchiveHabitTableName, selection, selectionArgs)
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
    //get user details by user id
    @SuppressLint("Range")
    fun getUserDetailsByUserId(userId: Int): User? {
        val db = this.readableDatabase

        val sqlStatement = "SELECT * FROM $UserTableName WHERE $User_Column_ID = ?"
        val param = arrayOf(userId.toString())
        val cursor: Cursor = db.rawQuery(sqlStatement, param)

        return if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndex(User_Column_UserName))
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
  /*  @SuppressLint("Range")
    fun getUserDetailsByUserId(userId: Int): User? {
        val db = this.readableDatabase
        var userID: User? = null

        val cursor = db.query(
            UserTableName, // Table name
            arrayOf(User_Column_FullName,  User_Column_Email, User_Column_PhoneNo,
                User_Column_UserName, User_Column_Password), // Columns to return
            "$User_Column_ID = ?", // Selection criteria
            arrayOf(userId.toString()), // Selection arguments
            null, // Group by
            null, // Having
            null // Order by
        )

        if (cursor.moveToFirst()) {
            val archivedHabitId = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_ID))
            val userId = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_UserId))
            val habitName = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_Name))
            val startDate = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_StartDate))
            val endDate = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_EndtDate))
            val target = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_Target))
            val prefTime = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_TimePreference))
            val currentCount = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_CurrentTargetCount))

            archviedhHabit = ArchiveHabit(archivedHabitId , userId, habitId, habitName , startDate, target, prefTime, currentCount, endDate)

        }

        cursor.close()
        db.close()
        return archviedhHabit
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
           val EndDate = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_EndtDate))
           val Target = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_Target))
           val PrefTime = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_TimePreference))
           val CurrentCount = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_CurrentTargetCount))

           val cart = ArchiveHabit(-1, 0, 0,  HabitName,StartDate, Target, PrefTime, CurrentCount , EndDate)
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
               Archive_Habit_Column_Target, Archive_Habit_Column_TimePreference, Archive_Habit_Column_CurrentTargetCount, Archive_Habit_Column_EndtDate), // Columns to return
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
           val endDate = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_EndtDate))
           val target = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_Target))
           val prefTime = cursor.getString(cursor.getColumnIndex(Archive_Habit_Column_TimePreference))
           val currentCount = cursor.getInt(cursor.getColumnIndex(Archive_Habit_Column_CurrentTargetCount))

           archviedhHabit = ArchiveHabit(archivedHabitId , userId, habitId, habitName , startDate, target, prefTime, currentCount, endDate)

       }

       cursor.close()
       db.close()
       return archviedhHabit
   }
    fun addHabitToArchive(archiveHabit: ArchiveHabit): Int {
        val db: SQLiteDatabase
        try {
            db = this.writableDatabase
        } catch (e: SQLiteException) {
            Log.e("DataBaseHelper", "Error opening writable database: ${e.message}")
            return -2
        }

        try {
            val cv: ContentValues = ContentValues()
            cv.put(Archive_Habit_Column_UserId, archiveHabit.archivedHabitUserId)
            cv.put(Archive_Habit_Column_Name, archiveHabit.archivedHabitName)
            cv.put(Archive_Habit_Column_StartDate, archiveHabit.archivedHabitStartDate)
            cv.put(Archive_Habit_Column_EndtDate, archiveHabit.archivedEndDate)
            cv.put(Archive_Habit_Column_Target, archiveHabit.archivedHabittarget)
            cv.put(Archive_Habit_Column_TimePreference, archiveHabit.archivedHabittimePreference)
            cv.put(Archive_Habit_Column_CurrentTargetCount, archiveHabit.archivedHabitcurrentCount)
            cv.put(Archive_Habit_Column_HabitId, archiveHabit.AhabitId)

            val success = db.insert(ArchiveHabitTableName, null, cv)
            if (success == -1L) {
                Log.e("DataBaseHelper", "addHabitToArchive: Error adding habit to archive.")
                return -1 // Error, adding new user
            } else {
                Log.d("DataBaseHelper", "addHabitToArchive: Successfully added habit to archive.")
                // Now attempt to delete the habit from the original table
                val deleteSuccess = db.delete(
                    HabitTableName,
                    "$Habit_Column_ID=?",
                    arrayOf(archiveHabit.AhabitId.toString())
                )

                if (deleteSuccess == 0) {
                    Log.e("DataBaseHelper", "Error deleting habit from original table.")
                    // Consider how you want to handle this case. For now, returning success since the archive operation was successful
                }
            }
        } finally {
            db.close() // Ensure the database is closed after all operations are complete
        }
        return 1 // Success
    }


    fun saveCurrentUserId(userId: Int, context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("currentUserId", userId)
        editor.apply()
    }
   /*fun saveCurrentUserId(userId: Int, username: String, context: Context) {
       val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
       val editor = sharedPreferences.edit()
       editor.putInt("currentUserId", userId)
       editor.putString("currentUsername", username)
       editor.apply()
   }*/
    fun getCurrentUserId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("currentUserId", -1) // Returns -1 if no user ID is found
    }
    fun saveCurrentAdminUserId(adminUserId: Int, context: Context) {
        val sharedPreferences = context.getSharedPreferences("AdminUserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("currentAdminUserId", adminUserId)
        editor.apply()
    }
    fun getCurrentAdminUserId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences("AdminUserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("currentAdminUserId", -1) // Returns -1 if no user ID is found
    }
    fun searchHabits(query: String): List<Habit> {
        val matchedHabits = mutableListOf<Habit>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $HabitTableName WHERE $Habit_Column_Name LIKE ?"
        db.rawQuery(selectQuery, arrayOf("%$query%")).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val habit = Habit(cursor)
                    matchedHabits.add(habit)
                    // Log the details of each habit as you add them to the list
                    Log.d("DataBaseHelper", "Found Habit: ${habit.toString()}")
                } while (cursor.moveToNext())
            } else {
                Log.d("DataBaseHelper", "No habits matched the query.")
            }
        }
        // Log the size of the list after searching
        Log.d("DataBaseHelper", "Number of habits matched: ${matchedHabits.size}")

        // If you want to log the entire list
        matchedHabits.forEach { habit ->
            Log.d("DataBaseHelper", "Habit: ${habit.habitName}, ID: ${habit.habitId}")
        }

        // Convert the MutableList to an immutable List before returning
        return matchedHabits.toList()
    }
    fun searchArchivedHabits(query: String): List<ArchiveHabit> {
        val matchedHabits = mutableListOf<ArchiveHabit>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $ArchiveHabitTableName WHERE $Archive_Habit_Column_Name LIKE ?"
        db.rawQuery(selectQuery, arrayOf("%$query%")).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val archivedHabit = ArchiveHabit(cursor)
                    matchedHabits.add(archivedHabit)
                    // Log the details of each habit as you add them to the list
                    Log.d("DataBaseHelper", "Found Habit: ${archivedHabit.toString()}")
                } while (cursor.moveToNext())
            } else {
                Log.d("DataBaseHelper", "No habits matched the query.")
            }
        }
        // Log the size of the list after searching
        Log.d("DataBaseHelper", "Number of habits matched: ${matchedHabits.size}")

        // If you want to log the entire list
        matchedHabits.forEach { habit ->
            Log.d("DataBaseHelper", "Habit: ${habit.archivedHabitName}, ID: ${habit.archivedHabitId}")
        }

        // Convert the MutableList to an immutable List before returning
        return matchedHabits.toList()
    }
    fun searchRecoHabits(query: String): List<RecoHabit> {
        val matchedHabits = mutableListOf<RecoHabit>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $RecoHabitTableName WHERE $RecoHabit_Column_RecoHabitName LIKE ?"
        db.rawQuery(selectQuery, arrayOf("%$query%")).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val recoHabit = RecoHabit(cursor)
                    matchedHabits.add(recoHabit)
                    // Log the details of each habit as you add them to the list
                    Log.d("DataBaseHelper", "Found Reco Habit: ${recoHabit.toString()}")
                } while (cursor.moveToNext())
            } else {
                Log.d("DataBaseHelper", "No Reco habits matched the query.")
            }
        }
        // Log the size of the list after searching
        Log.d("DataBaseHelper", "Number of Reco habits matched: ${matchedHabits.size}")

        // If you want to log the entire list
        matchedHabits.forEach { habit ->
            Log.d("DataBaseHelper", "Recommanded Habit: ${habit.recoHabitName}, ID: ${habit.recohabitId}")
        }
        // Convert the MutableList to an immutable List before returning
        return matchedHabits.toList()
    }

    fun searchRecoHabitsAdmin(query: String): List<RecoHabit> {
        val matchedHabits = mutableListOf<RecoHabit>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $RecoHabitTableName WHERE $RecoHabit_Column_RecoHabitName LIKE ?"
        db.rawQuery(selectQuery, arrayOf("%$query%")).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val recoHabit = RecoHabit(cursor)
                    matchedHabits.add(recoHabit)
                    // Log the details of each habit as you add them to the list
                    Log.d("DataBaseHelper", "Found Reco Habit: ${recoHabit.toString()}")
                } while (cursor.moveToNext())
            } else {
                Log.d("DataBaseHelper", "No Reco habits matched the query.")
            }
        }
        // Log the size of the list after searching
        Log.d("DataBaseHelper", "Number of Reco habits matched: ${matchedHabits.size}")

        // If you want to log the entire list
        matchedHabits.forEach { habit ->
            Log.d("DataBaseHelper", "Recommanded Habit: ${habit.recoHabitName}, ID: ${habit.recohabitId}")
        }
        // Convert the MutableList to an immutable List before returning
        return matchedHabits.toList()
    }
    //Notifications
   /* fun getHabitsNearEndDate(reminderDate: String): Cursor {
        val db = this.readableDatabase
        val query = """
            SELECT $Habit_Column_ID, $Habit_Column_Name FROM $HabitTableName 
            WHERE $Habit_Column_EndDate = ? AND $Habit_Column_CurrentTargetCount < $Habit_Column_Target
        """
        return db.rawQuery(query, arrayOf(reminderDate))
    }*/
    fun getHabitsNearEndDate(formattedReminderDate: String): Cursor {
        val db = this.readableDatabase
        val query = """
        SELECT $Habit_Column_ID, $Habit_Column_Name FROM $HabitTableName 
        WHERE $Habit_Column_EndDate = ? AND $Habit_Column_CurrentTargetCount < $Habit_Column_Target
    """
        Log.d("DataBaseHelper", "Query: $query")
        val cursor = db.rawQuery(query, arrayOf(formattedReminderDate))
        Log.d("DataBaseHelper", "Cursor count: ${cursor.count}")
        return cursor
    }
    companion object {
        private const val Habit_Column_Name = "HabitName"

        val habitColumnName: String
            get() = Habit_Column_Name

    }
}