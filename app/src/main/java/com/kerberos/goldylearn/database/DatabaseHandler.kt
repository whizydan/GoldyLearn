package com.kerberos.goldylearn.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kerberos.goldylearn.models.Course
import com.kerberos.goldylearn.models.LearningProgress
import com.kerberos.goldylearn.utils.TinyDB
import com.kerberos.goldylearn.utils.Utils
import java.io.File

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var mContext:Context = context

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "leaner_database.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createStatsTable = "CREATE TABLE \"today\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"date\"\tTEXT,\n" +
                "\t\"duration\"\tINTEGER,\n" +
                "\t\"total\"\tINTEGER,\n" +
                "\t\"course_id\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");"
        val createTableQuery = "CREATE TABLE \"course\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"duration\"\tINTEGER,\n" +
                "\t\"category\"\tTEXT,\n" +
                "\t\"progress\"\tINTEGER DEFAULT 1,\n" +
                "\t\"filename\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");"
        db.execSQL(createTableQuery)
        db.execSQL(createStatsTable)
    }
    public fun clearStats(){
        val date = Utils(mContext).getDate()
        val query = "delete from today where date != '$date'"
        this.writableDatabase.use {
            it.execSQL(query)
        }
    }
    public fun insertStat(date:String,duration:Int,total:Int,course_id:String){
        val values = ContentValues()
        values.put("date",date)
        values.put("duration",duration)
        values.put("total",total)
        values.put("course_id",course_id)

        this.writableDatabase.use {
            it.insert("today",null,values)
        }
    }

    public fun getHistory():MutableList<Course>
    {
        val history = mutableListOf<Course>()
        this.writableDatabase.use {
            val historyCursor = it.rawQuery("select * from course inner join today on course.id = today.course_id",null)
            while (historyCursor.moveToNext()){
                val course = Course(
                    historyCursor.getInt(10),
                    historyCursor.getString(1),
                    historyCursor.getInt(2),
                    historyCursor.getString(3),
                    historyCursor.getInt(4),
                    historyCursor.getString(5)
                )
                history.add(course)
            }
            historyCursor.close()
        }
        return history
    }


    public fun getStats():MutableList<Int>
    {
        var date = Utils(mContext).getDate()
        date = date.substring(0,9)
        val query = "select * from today where date like '%$date%'"
        var totalTime :Int = 1
        var totalDoneTime : Int = 1
        val returnData = mutableListOf<Int>()
        val db = this.writableDatabase

            val cursor = db.rawQuery(query,null)
            while (cursor.moveToNext()){
                totalTime = totalTime.plus(cursor.getInt(3))
                totalDoneTime = totalDoneTime.plus(cursor.getInt(2))
            }

            returnData.add(totalTime)
            returnData.add(totalDoneTime)
            cursor.close()


        return returnData
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade if needed
    }

    fun insertCourses() {
        val tinyDb = TinyDB(mContext)
        val db = this.writableDatabase
        //create an array list
        val courses = mutableListOf<Course>()
        courses.add(Course(1,"How to make a call",57,"accessibility",0,"https://www.youtube.com/watch?v=0g7whEfqTpc&ab_channel=yournewphone"))
        courses.add(Course(2,"How to reset the device",120,"settings",0,"https://www.youtube.com/watch?v=RGZ1i5MpWCw&ab_channel=HardReset.Info"))
        courses.add(Course(3,"Changing the ringtone",170,"accessibility",0,"https://www.youtube.com/watch?v=bHqsgIET790&ab_channel=LoFiAlpaca"))
        courses.add(Course(4,"Sending an sms",123,"communication",0,"https://www.youtube.com/watch?v=rfA83_bhdXw&ab_channel=MobileHowTo"))
        courses.add(Course(5,"Uninstalling an app",90,"settings",0,"https://www.youtube.com/watch?v=Jm4UNg9GOUE&ab_channel=GuidingTech"))

        courses.forEach {
            val values = ContentValues()
            values.put("name", it.name)
            values.put("id",it.id)
            values.put("duration",it.duration)
            values.put("progress",it.progress)
            values.put("filename",it.filename)
            values.put("category",it.category)
            db.insert("course", null, values)
        }
        tinyDb.putString("firstRun","no")
    }

    fun getAllCourses(): List<Course> {
        val dataList = mutableListOf<Course>()
        val selectQuery = "SELECT * FROM course"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val course = Course(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5)
                )
                dataList.add(course)
            } while (cursor.moveToNext())
        }

        cursor.close()
        //db.close()
        return dataList
    }
    fun getCourseById(id: String?):Course?
    {
        var course:Course? = null
        val cursor = this.writableDatabase.rawQuery("select * from course where id='$id'",null)
        if(cursor.moveToFirst()){
        course = Course(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getInt(2),
            cursor.getString(3),
            cursor.getInt(4),
            cursor.getString(5)
        )
        }
        cursor.close()
        return course
    }
    fun getProgress(): MutableList<LearningProgress> {
        //get all categories,then loop through for each
        val getAllCategories = "select DISTINCT category from course"
        val categories = mutableListOf<String>()
        val progressList = mutableListOf<LearningProgress>()
        this.writableDatabase.use { it ->
            //get all categories and store them
            val categoryCursor = it.rawQuery(getAllCategories,null)
            while (categoryCursor.moveToNext()){
                val categoryName = categoryCursor.getString(0)
                categories.add(categoryName)
            }
            categoryCursor.close()

            //loop through each category and store the data
            categories.forEach {value->
                val categoriesCursor = it.rawQuery("select * from course where category = '$value'",null)
                val progress = LearningProgress()
                progress.totalCategories = categories.size
                progress.category = value
                var completedCategories = 0
                var progressValue = 0
                var totalItemsInCategory = 0

                while (categoriesCursor.moveToNext()){
                    totalItemsInCategory += 1
                    progressValue += categoriesCursor.getInt(4)
                    if(progressValue >= categoriesCursor.getInt(2)){
                        completedCategories += 1
                    }
                }

                progress.totalCategories = totalItemsInCategory
                progress.completedCategories = completedCategories
                if(progressValue == 0){
                    progress.progress = 0
                }else{
                    if (totalItemsInCategory != 0){
                        progress.progress = (progressValue/totalItemsInCategory)*100
                    }else{
                        progress.progress = 0
                    }
                }
                categoriesCursor.close()
                progressList.add(progress)
            }
        }

        return progressList
    }

    fun updateProgress(id: String,progress:Int){
        val db = this.writableDatabase
        val query = "update course set progress = $progress where id='$id'"
        try {
            db.execSQL(query)
        }catch (exception:Exception){
            alert("Could not update progress",exception.localizedMessage)
        }

    }
    fun alert(title:String, message:String){
        MaterialAlertDialogBuilder(mContext)
            .setTitle(title)
            .setMessage(message)
            .show()
    }
    fun getDatabase():File
    {
        return File(this.writableDatabase.path)
    }
    fun getCompletedCourses():MutableList<Course>
    {
        val courses = mutableListOf<Course>()
        val db = this.writableDatabase
        val query = "select * from course where progress >= duration"
        val cursor = db.rawQuery(query,null)
        while (cursor.moveToNext()){
            val course = Course(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getString(5)
            )
            courses.add(course)
        }
        cursor.close()
        return courses
    }
}
