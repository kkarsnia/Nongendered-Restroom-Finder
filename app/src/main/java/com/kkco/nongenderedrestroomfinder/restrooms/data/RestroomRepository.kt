package com.kkco.nongenderedrestroomfinder.restrooms.data

import com.kkco.nongenderedrestroomfinder.data.resultLiveData
import com.kkco.nongenderedrestroomfinder.maps.data.LocationDetails
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
    fun observeLocation(locationDetails: LocationDetails) = resultLiveData(
        databaseQuery = { dao.getRestrooms() },
        networkCall = { remoteSource.fetchData(locationDetails) },
        saveCallResult = { dao.insertAll(it) })
}