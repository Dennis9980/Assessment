package org.d3if0119.pomodoroappnew.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TasksDao {
    @Insert
    fun insert(tasks: TaskEntity)

    @Query ("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<TaskEntity>>

    @Delete
    fun delete(tasks: TaskEntity)
}