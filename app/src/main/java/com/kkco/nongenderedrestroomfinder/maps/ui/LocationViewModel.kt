package com.kkco.nongenderedrestroomfinder.maps.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kkco.nongenderedrestroomfinder.maps.data.LocationDetails
import com.kkco.nongenderedrestroomfinder.maps.data.LocationLiveData
import com.kkco.nongenderedrestroomfinder.restrooms.data.RestroomRepository
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    application: Application,
    repository: RestroomRepository
) : AndroidViewModel(application) {

    // Get the current location to pass to API
    lateinit var locationDetails: LocationDetails
    val locationData by lazy { repository.observeLocation(locationDetails) }

    private val locationLiveData = LocationLiveData(application)
    internal fun getLocationLiveData() = locationLiveData
}