package com.kkco.nongenderedrestroomfinder.maps.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kkco.nongenderedrestroomfinder.maps.data.LocationLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData = LocationLiveData(application)
    internal fun getLocationLiveData() = locationLiveData
}