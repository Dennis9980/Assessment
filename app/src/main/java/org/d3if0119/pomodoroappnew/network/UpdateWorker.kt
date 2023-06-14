package org.d3if0119.pomodoroappnew.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class UpdateWorker (context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters){
    companion object{
        const val WORK_NAME = "updater"
    }

    override suspend fun doWork(): Result {
        Log.d("Worker", "Sedang berjalan di background")
        return Result.success()
    }
}