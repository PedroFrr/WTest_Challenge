package com.pedrofr.wtest.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.pedrofr.wtest.data.db.AppDatabase
import com.pedrofr.wtest.data.db.entities.DbPostcode
import kotlinx.coroutines.flow.asFlow
import java.io.File

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
                postcodeNumber = row.getValue("num_cod_postal")
            )
        }

//        val postcodes = mutableListOf<DbPostcode>()
//
//        //skipMissMatchedRow true means invalid rows are skipped and no exception is thrown
//        csvReader {
//            skipMissMatchedRow = true
//        }.open(file) {
//
//            readAllWithHeaderAsSequence().forEach { row: Map<String, String> ->
//                val postcode = DbPostcode(
//                    postalDesignation = row.getValue("desig_postal"),
//                    postcodeExtension = row.getValue("ext_cod_postal"),
//                    postcodeNumber = row.getValue("num_cod_postal")
//                )
//                postcodes.add(postcode)
//            }
//        }

        val database = AppDatabase.getInstance(applicationContext)
        database.postcodeDao().insertAll(postcodes)

        return Result.success()
    }

}

