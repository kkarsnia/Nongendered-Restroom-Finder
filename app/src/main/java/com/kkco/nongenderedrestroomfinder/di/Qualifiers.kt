package com.kkco.nongenderedrestroomfinder.di

import javax.inject.Qualifier

//TODO: fix these, i don't think they are named right or something, research more
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RestroomAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeIO