package org.d3if0119.pomodoroappnew.ui.home

import android.app.Application
import android.text.format.Time
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import org.d3if0119.pomodoroappnew.network.UpdateWorker
import java.util.concurrent.TimeUnit

class HomeViewModel : ViewModel(){
    fun schedulUpdater(app: Application){
        val request = OneTimeWorkRequestBuilder<UpdateWorker>().setInitialDelay(1, TimeUnit.MINUTES).build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}