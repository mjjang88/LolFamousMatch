package com.mjjang.lolfamousmatch.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.mjjang.lolfamousmatch.data.AppDatabase
import com.mjjang.lolfamousmatch.data.Match
import com.mjjang.lolfamousmatch.utilities.MATCH_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(MATCH_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val matchType = object : TypeToken<List<Match>>() {}.type
                    val matchList: List<Match> = Gson().fromJson(jsonReader, matchType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.matchDao().insertAll(matchList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}