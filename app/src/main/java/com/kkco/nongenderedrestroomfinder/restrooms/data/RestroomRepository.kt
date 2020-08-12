package com.kkco.nongenderedrestroomfinder.restrooms.data

import com.kkco.nongenderedrestroomfinder.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class RestroomRepository @Inject constructor(
    private val dao: RestroomDao,
    private val remoteSource: RestroomRemoteDataSource
) {
    val restrooms = resultLiveData(
        databaseQuery = { dao.getRestrooms() },
        networkCall = { remoteSource.fetchData() },
        saveCallResult = { dao.insertAll(it.results) })
}