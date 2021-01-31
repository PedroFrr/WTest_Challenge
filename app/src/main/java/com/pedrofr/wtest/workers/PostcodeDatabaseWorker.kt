package com.pedrofr.wtest.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.pedrofr.wtest.data.db.AppDatabase
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.util.removeNonSpacingMarks
import java.io.File

class PostcodeDatabaseWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val csvPath = inputData.getString("csv_path") ?: return Result.failure()

        val file = File(csvPath)

        val rows = csvReader { skipMissMatchedRow = true }.readAllWithHeader(file)

        //Removes repeated postcodes for same postal designation.
        // On the CSV they are not "repeated" since for the same postcode we have different addresses (streets, etc.).
        // Since we're not interested in the addresses we remove it
        val rowsDistinct = rows.distinctBy {
            Triple( it.getValue("desig_postal"),
            it.getValue("ext_cod_postal"),
            it.getValue("num_cod_postal"))
        }

        val postcodes = rowsDistinct.map { row ->
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

