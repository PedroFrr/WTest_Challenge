package com.pedrofr.wtest.data.db

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.pedrofr.wtest.data.db.dao.ArticleDao
import com.pedrofr.wtest.data.db.dao.PostcodeDao
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.data.db.entities.DbPostcodeFTS
import com.pedrofr.wtest.services.DownloadPostcodeCsvReceiver
import com.pedrofr.wtest.util.DATABASE_NAME
import com.pedrofr.wtest.workers.ClearLocalStorageWorker
import com.pedrofr.wtest.workers.DownloadPostcodeCsvWorker
import com.pedrofr.wtest.workers.PostcodeDatabaseWorker

/**
 * SQLite Database for storing the logs.
 */
@Database(entities = [DbPostcode::class, DbArticle::class, DbPostcodeFTS::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postcodeDao(): PostcodeDao
    abstract fun articleDao(): ArticleDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //TODO set constraints network, storage so the operation succeeds - test if no network it will eventually download. Show error
                            val downloadPostcodeCsvWorker = OneTimeWorkRequestBuilder<DownloadPostcodeCsvWorker>().build()
                            val postcodeDatabaseWorker = OneTimeWorkRequestBuilder<PostcodeDatabaseWorker>().build()
                            val clearLocalStorageWorker = OneTimeWorkRequestBuilder<ClearLocalStorageWorker>().build()
                            //TODO add comment
                            WorkManager.getInstance(context)
                                .beginWith(downloadPostcodeCsvWorker)
                                .then(postcodeDatabaseWorker)
                                .then(clearLocalStorageWorker)
                                .enqueue()

                        }
                    }
                )
                .build()
        }
    }

}