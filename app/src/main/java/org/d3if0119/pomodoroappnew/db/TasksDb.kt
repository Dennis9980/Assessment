package org.d3if0119.pomodoroappnew.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TaskEntity::class], version = 1)
abstract class TasksDb : RoomDatabase() {
    abstract fun taskDao(): TasksDao

    companion object{
        @Volatile
        private var instance: TasksDb? = null

        fun getInstance(context: Context): TasksDb{
            return instance ?: synchronized(this){
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDb::class.java,
                    "app_database"
                ).build()
                instance = database
                database
            }
        }
    }
}