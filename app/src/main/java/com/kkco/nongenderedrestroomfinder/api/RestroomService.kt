package com.kkco.nongenderedrestroomfinder.api

import com.kkco.nongenderedrestroomfinder.restrooms.data.Restroom
import com.kkco.nongenderedrestroomfinder.util.PARAM_LAT
import com.kkco.nongenderedrestroomfinder.util.PARAM_LNG
import com.kkco.nongenderedrestroomfinder.util.PARAM_OFFSET
import com.kkco.nongenderedrestroomfinder.util.PARAM_PAGE
import com.kkco.nongenderedrestroomfinder.util.PARAM_PER_PAGE
import com.kkco.nongenderedrestroomfinder.util.PARAM_UNISEX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Restroom REST API access points
 */
interface RestroomService {

    companion object {
        const val ENDPOINT = "https://www.refugerestrooms.org/api/v1/"
    }

    @GET("restrooms/by_location")
    suspend fun getRestroomsRemote(
        @Query(PARAM_PAGE) page: String,
        @Query(PARAM_PER_PAGE) per_page: String,
        @Query(PARAM_OFFSET) offset: String,
        @Query(PARAM_UNISEX) unisex: String,
        @Query(PARAM_LAT) lat: String,
        @Query(PARAM_LNG) lng: String
    ): Response<List<Restroom>>
}