package com.pedrofr.wtest.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Worker that deletes all files stored on the app media
 * This is done because once the CSV content is inserted into the AppDatabase we will not need it anymore
 */
class ClearLocalStorageWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        val rootFolder = applicationContext.externalMediaDirs.first()

        rootFolder?.listFiles()?.forEach {
            if (it.isDirectory) {
                it.deleteRecursively()
            } else {
                it.delete()
            }
        }
        return Result.success()
    }
}