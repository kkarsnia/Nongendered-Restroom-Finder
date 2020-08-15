package com.kkco.nongenderedrestroomfinder.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class RestroomServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: RestroomService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestroomService::class.java)
    }

    @Test
    fun requestRestrooms() {
        runBlocking {
            enqueueResponse("restrooms.json")
            val resultResponse = service.getRestrooms().body()

            val request = mockWebServer.takeRequest()
            Assert.assertNotNull(resultResponse)
            Assert.assertThat(
                request.path,
                CoreMatchers.`is`("restrooms/by_location?page=1&per_page=10&offset=0&unisex=true&lat=28.555746&lng=-81.375192")
            )
        }
    }

    @Test
    fun getRestroomsResponse() {
        runBlocking {
            enqueueResponse("restrooms.json")
            val resultResponse = service.getRestrooms().body()
            val restrooms = resultResponse!!.results

            Assert.assertThat(resultResponse.count, CoreMatchers.`is`(9))
            Assert.assertThat(restrooms.size, CoreMatchers.`is`(9))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(
                source.readString(Charsets.UTF_8)
            )
        )
    }
}