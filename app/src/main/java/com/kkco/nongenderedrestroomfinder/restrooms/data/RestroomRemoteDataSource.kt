package com.kkco.nongenderedrestroomfinder.restrooms.data

import com.kkco.nongenderedrestroomfinder.api.BaseDataSource
import com.kkco.nongenderedrestroomfinder.api.RestroomService
import javax.inject.Inject

class RestroomRemoteDataSource @Inject constructor(private val service: RestroomService) :
    BaseDataSource() {

    //TODO: update with proper params from service
    suspend fun fetchData() = getResult { service.getRestrooms() }

    // @Query("page") page: Int? = null,
    // @Query("per_page") perPage: Int? = null,
    // @Query("offset") offset: String? = null,
    // @Query("ada") ada: Boolean? = null,
    // @Query("unisex") unisex: Boolean? = null,
    // @Query("lat") lat: Float? = null,
    // @Query("long") long: Float? = null
}