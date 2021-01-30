package com.pedrofr.wtest.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.pedrofr.wtest.data.db.AppDatabase
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.util.removeNonSpacingMarks
import kotlinx.coroutines.flow.asFlow
import java.io.File
import java.text.Normalizer

class PostcodeDatabaseWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val csvPath = inputData.getString("csv_path") ?: return Result.failure()

        val file = File(csvPath)


        val rows = csvReader { skipMissMatchedRow = true }.readAllWithHeader(file)

        val postcodes = rows.map { row ->
            DbPostcode(
                postalDesignation = row.getValue("desig_postal"),
                postcodeExtension = row.getValue("ext_cod_postal"),
                postcodeNumber = row.getValue("num_cod_postal"),
                //this column is created to search with accents (ã, é ...)
                //the original value is normalized and set to lowercase
                postalDesignationAscii = row.getValue("desig_postal").removeNonSpacingMarks()
            )
        }

        val database = AppDatabase.getInstance(applicationContext)
        database.postcodeDao().insertAll(postcodes)

        return Result.success()
    }

}

