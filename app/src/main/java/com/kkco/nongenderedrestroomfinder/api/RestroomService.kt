package com.kkco.nongenderedrestroomfinder.api

import com.kkco.nongenderedrestroomfinder.restrooms.data.Restroom
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

    //https://www.refugerestrooms.org/api/v1/restrooms/by_location?page=1&per_page=10&offset=0&unisex=true&lat=28.555680&lng=-81.375171
    //SERVER_URL + "restrooms/by_location.json?per_page=20&" + searchTerm;
    //@GET("restrooms/by_location")
    //TODO: update this to pass in lat/long params
    // @GET("api/v1/restrooms/by_location?page=1&per_page=10&offset=0&unisex=true&lat=28.555746&lng=-81.375192")
    @GET("api/v1/restrooms/by_location")
    suspend fun getRestrooms(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("offset") offset: String? = null,
        @Query("ada") ada: Boolean? = null,
        @Query("unisex") unisex: Boolean? = null,
        @Query("lat") lat: Float? = null,
        @Query("long") long: Float? = null
    ): Response<ResultsResponse<Restroom>>
}