package org.d3if0119.pomodoroappnew.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0119.pomodoroappnew.db.TasksDao

class TasksViewModelFactory (private val db : TasksDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)){
            return TasksViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}