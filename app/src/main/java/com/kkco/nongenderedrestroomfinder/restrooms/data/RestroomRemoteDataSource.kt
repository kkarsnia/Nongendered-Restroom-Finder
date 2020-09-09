package com.kkco.nongenderedrestroomfinder.restrooms.data

import com.kkco.nongenderedrestroomfinder.api.BaseDataSource
import com.kkco.nongenderedrestroomfinder.api.RestroomService
import javax.inject.Inject

class RestroomRemoteDataSource @Inject constructor(private val service: RestroomService) :
    BaseDataSource() {

    //TODO: update with proper params from service

    // suspend fun fetchData() = getResult { service.getRestrooms() }
    // @GET("restrooms/by_location?page=1&per_page=20&offset=0&unisex=true&lat=28.555746&lng=-81.375192")
    suspend fun fetchData() = getResult { service.getRestroomsRemote() }

    // @Query("page") page: Int? = null,
    // @Query("per_page") perPage: Int? = null,
    // @Query("offset") offset: String? = null,
    // @Query("ada") ada: Boolean? = null,
    // @Query("unisex") unisex: Boolean? = null,
    // @Query("lat") lat: Float? = null,
    // @Query("long") long: Float? = null
}