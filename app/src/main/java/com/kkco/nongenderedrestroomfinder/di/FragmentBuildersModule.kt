package com.kkco.nongenderedrestroomfinder.di

import com.kkco.nongenderedrestroomfinder.maps.MapsFragment
import com.kkco.nongenderedrestroomfinder.restrooms.ui.RestroomListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeRestroomListFragment(): RestroomListFragment

    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapsFragment
}