package com.kkco.nongenderedrestroomfinder.restrooms.data

import com.kkco.nongenderedrestroomfinder.api.BaseDataSource
import com.kkco.nongenderedrestroomfinder.api.RestroomService
import com.kkco.nongenderedrestroomfinder.maps.data.LocationDetails
import com.kkco.nongenderedrestroomfinder.util.VALUE_OFFSET
import com.kkco.nongenderedrestroomfinder.util.VALUE_PAGE
import com.kkco.nongenderedrestroomfinder.util.VALUE_PER_PAGE
import com.kkco.nongenderedrestroomfinder.util.VALUE_UNISEX
import javax.inject.Inject

class RestroomRemoteDataSource @Inject constructor(private val service: RestroomService) :
    BaseDataSource() {

    suspend fun fetchData(locationDetails: LocationDetails) = getResult {
        service.getRestroomsRemote(
            VALUE_PAGE,
            VALUE_PER_PAGE,
            VALUE_OFFSET,
            VALUE_UNISEX,
            locationDetails.latitude.toString(),
            locationDetails.longitude.toString()
        )
    }
}