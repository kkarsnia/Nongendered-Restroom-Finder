package com.kkco.nongenderedrestroomfinder.restrooms.ui

import androidx.lifecycle.ViewModel
import com.kkco.nongenderedrestroomfinder.restrooms.data.RestroomRepository
import javax.inject.Inject

/**
 * The ViewModel for [RestroomListFragment].
 */
class RestroomListViewModel @Inject constructor(repository: RestroomRepository) : ViewModel() {
    val restrooms = repository.restrooms
}