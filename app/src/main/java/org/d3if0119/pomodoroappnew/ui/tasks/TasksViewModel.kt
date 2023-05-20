package org.d3if0119.pomodoroappnew.ui.tasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0119.pomodoroappnew.db.TaskEntity
import org.d3if0119.pomodoroappnew.db.TasksDao

class TasksViewModel(private val tasksDao: TasksDao): ViewModel() {
    val tasksLiveData: LiveData<List<TaskEntity>> = tasksDao.getAllTasks()

    fun insertTask(task: TaskEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                tasksDao.insert(task)
            }
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                tasksDao.delete(task)
            }
        }
    }
}