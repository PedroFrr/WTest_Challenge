package com.pedrofr.wtest.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.pedrofr.wtest.data.db.AppDatabase
import com.pedrofr.wtest.data.db.entities.DbPostcode
import java.io.File

class PostcodeDatabaseWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val csvPath = inputData.getString("csv_path") ?: return Result.failure()

        val file = File(csvPath)
        val rows: List<Map<String, String>> = csvReader().readAllWithHeader(file)

        val postcodes = rows.map {
            DbPostcode(
                postalDesignation = it.getValue("desig_postal"),
                postcodeExtension = it.getValue("ext_cod_postal"),
                postcodeNumber = it.getValue("num_cod_postal")
            )
        }

        val database = AppDatabase.getInstance(applicationContext)
        database.postcodeDao().insertAll(postcodes)



        return Result.success()
    }

}