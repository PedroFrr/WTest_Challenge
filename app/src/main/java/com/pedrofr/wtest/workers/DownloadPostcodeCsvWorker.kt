package com.pedrofr.wtest.workers

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.pedrofr.wtest.util.POSTCODE_BASE_URL
import java.io.File

//TODO see if I should change this to a normal worker
class DownloadPostcodeCsvWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val isDownloaded = false

    companion object {
        private const val TAG = "Worker"
    }


    override fun doWork(): Result {
        try {
            Log.d(TAG, "PostcodeDatabaseWorker")


            val csvPath = "${System.currentTimeMillis()}.csv"
            val file = File(applicationContext.externalMediaDirs.first(), csvPath)

            val request = DownloadManager.Request(Uri.parse(POSTCODE_BASE_URL))
                .setTitle("Postcode Csv download")
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(false)

            val downloadManager = applicationContext.getSystemService(DownloadManager::class.java)
            val downloadId = downloadManager?.enqueue(request)

            val output = workDataOf("csv_path" to file.absolutePath) //saves the csvPath on the output so we can use it on the next chained worker
            return Result.success(output)

        } catch (error: Exception) {
            return Result.failure()
        }


    }



}