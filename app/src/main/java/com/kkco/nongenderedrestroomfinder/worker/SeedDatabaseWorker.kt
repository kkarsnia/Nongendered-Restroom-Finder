package com.kkco.nongenderedrestroomfinder.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.kkco.nongenderedrestroomfinder.data.AppDatabase
import com.kkco.nongenderedrestroomfinder.restrooms.data.Restroom
import com.kkco.nongenderedrestroomfinder.util.DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {

            try {
                applicationContext.assets.open(DATA_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        // val type = object : TypeToken<List<Restroom>>() {}.type
                        // val list: List<Restroom> = Gson().fromJson(jsonReader, type)
                        // val type = Array<Restroom>::class.java
                        val gson = GsonBuilder().create()
                        val list: List<Restroom> = gson.fromJson(jsonReader, Restroom::class.java)
                        Log.d("SeedDatabaseWorker", "list: $list")

                        // AppDatabase.getInstance(applicationContext).restroomDao().insertAll(list)
                        AppDatabase.getInstance(applicationContext).restroomDao().insertAll(list)

                        Result.success()
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error seeding database")
                Result.failure()
            }
        }
    }
}