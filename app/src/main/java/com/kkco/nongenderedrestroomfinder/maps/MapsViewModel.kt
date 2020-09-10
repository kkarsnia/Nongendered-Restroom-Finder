package com.kkco.nongenderedrestroomfinder.maps

import androidx.lifecycle.ViewModel
import com.kkco.nongenderedrestroomfinder.restrooms.data.RestroomRepository
import javax.inject.Inject

/**
 * The ViewModel for [MapsFragment].
 */
class MapsViewModel @Inject constructor(repository: RestroomRepository) : ViewModel() {
    // val restrooms = repository.restrooms
}