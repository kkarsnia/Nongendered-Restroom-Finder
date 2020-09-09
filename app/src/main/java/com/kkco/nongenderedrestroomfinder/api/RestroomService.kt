package com.kkco.nongenderedrestroomfinder.api

import com.kkco.nongenderedrestroomfinder.maps.data.LocationDetails
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

    val location: LocationDetails

    //https://www.refugerestrooms.org/api/v1/restrooms/by_location?page=1&per_page=10&offset=0&unisex=true&lat=28.555680&lng=-81.375171
    //SERVER_URL + "restrooms/by_location.json?per_page=20&" + searchTerm;
    //@GET("restrooms/by_location")
    //TODO: update this to pass in lat/long params and turn into QueryMap
    // @GET("api/v1/restrooms/by_location?page=1&per_page=10&offset=0&unisex=true&lat=28.555746&lng=-81.375192")
    // restrooms/by_location.json?per_page=20&
    @GET("restrooms/by_location?page=1&per_page=20&offset=0&unisex=true&lat=28.555746&lng=-81.375192")
    suspend fun getRestrooms(): Response<List<Restroom>>

    @GET("restrooms/by_location")
    suspend fun getRestroomsRemote(
        @Query("page") page: String,
        @Query("per_page") per_page: String,
        @Query("offset") offset: String,
        @Query("unisex") unisex: String,
        @Query("lat") lat: String,
        @Query("lng") lng: String
    ): Response<List<Restroom>>

    //this one is working-KK_09092020_1619
    // @GET("restrooms/by_location?page=1&per_page=20&offset=0&unisex=true&lat=28.555746&lng=-81.375192")
    // suspend fun getRestrooms(): Response<List<Restroom>>

    // fun getSearchTermFromLatLng(
    //     latitude: Double,
    //     longitude: Double
    // ): String? {
    //     return "&lat=$latitude&lng=$longitude"
    // }

    //possible params:
    // @Query("page") page: Int? = null,
    // @Query("per_page") perPage: Int? = null,
    // @Query("offset") offset: String? = null,
    // @Query("ada") ada: Boolean? = null,
    // @Query("unisex") unisex: Boolean? = null,
    // @Query("lat") lat: Float? = null,
    // @Query("long") long: Float? = null
}