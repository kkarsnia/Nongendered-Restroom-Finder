package com.kkco.nongenderedrestroomfinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kkco.nongenderedrestroomfinder.maps.MapsViewModel
import com.kkco.nongenderedrestroomfinder.restrooms.ui.RestroomListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RestroomListViewModel::class)
    abstract fun bindRestroomListViewModel(viewModel: RestroomListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun bindMapsViewModel(viewModel: MapsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}