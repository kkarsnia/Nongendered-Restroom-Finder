package com.kkco.nongenderedrestroomfinder.di

import android.app.Application
import com.kkco.nongenderedrestroomfinder.api.RestInterceptor
import com.kkco.nongenderedrestroomfinder.api.RestroomService
import com.kkco.nongenderedrestroomfinder.data.AppDatabase
import com.kkco.nongenderedrestroomfinder.restrooms.data.RestroomRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideRestroomService(
        @RestroomAPI okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, RestroomService::class.java)

    @Singleton
    @Provides
    fun provideRestroomRemoteDataSource(restroomService: RestroomService) =
        RestroomRemoteDataSource(restroomService)

    @RestroomAPI
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(RestInterceptor())
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideRestroomDao(db: AppDatabase) = db.restroomDao()

    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RestroomService.ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory, clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}