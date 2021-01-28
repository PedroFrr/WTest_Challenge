package com.pedrofr.wtest.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.pedrofr.wtest.util.POSTCODE_BASE_URL
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

//TODO see if I should change this to a coroutine worker
class DownloadPostcodeCsvWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    companion object {
        private const val TAG = "Worker"
    }


    override fun doWork(): Result {
        try {
            Log.d(TAG, "Started downloading...")
            val csvUrl = URL(POSTCODE_BASE_URL) //TODO check if we should instead pass it in as input_data

            val connection = csvUrl.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val csvPath = "${System.currentTimeMillis()}.csv"
            val inputStream = connection.inputStream
            val file = File(applicationContext.externalMediaDirs.first(), csvPath)

            val outputStream = FileOutputStream(file)
            outputStream.use { output ->
                val buffer = ByteArray(4*1024) //TODO change
                var byteCount = inputStream.read(buffer)

                while(byteCount > 0) {
                    output.write(buffer, 0, byteCount)
                    byteCount = inputStream.read(buffer)
                }

                output.flush()
            }

            val output = workDataOf("csv_path" to file.absolutePath) //saves the csvPath on the output so we can use it on the next chained worker
            return Result.success(output)

        } catch (error: Exception) {
            return Result.failure()
        }


    }

}