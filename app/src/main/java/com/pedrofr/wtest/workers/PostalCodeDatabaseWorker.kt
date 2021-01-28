package com.pedrofr.wtest.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class PostalCodeDatabaseWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object {
        private const val TAG = "Worker"
    }


    override suspend fun doWork(): Result {
        try {
            Log.d(TAG, "Started work")
            val csvUrl =
                URL("https://raw.githubusercontent.com/centraldedados/codigos_postais/master/data/codigos_postais.csv")
            val bufferedReader = BufferedReader(InputStreamReader(csvUrl.openStream()))
            val reader = CSVReader(bufferedReader)

            bufferedReader.readLines().forEach {
                val items = it.split(",")
                Log.d(TAG, items.toString())

            }


//            val csvParser = CsvP(reader, CSVFormat.DEFAULT
//                .withFirstRecordAsHeader()
//                .withIgnoreHeaderCase()
//                .withTrim())

            return Result.success()

        } catch (error: Exception) {
            return Result.failure()
        }


    }

}