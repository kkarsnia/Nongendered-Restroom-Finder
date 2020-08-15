package com.kkco.nongenderedrestroomfinder.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kkco.nongenderedrestroomfinder.api.RestroomService
import com.kkco.nongenderedrestroomfinder.data.AppDatabase
import com.kkco.nongenderedrestroomfinder.restrooms.data.RestroomDao
import com.kkco.nongenderedrestroomfinder.restrooms.data.RestroomRemoteDataSource
import com.kkco.nongenderedrestroomfinder.restrooms.data.RestroomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@RunWith(JUnit4::class)
class RestroomRepositoryTest {
    private lateinit var repository: RestroomRepository
    private val dao = Mockito.mock(RestroomDao::class.java)
    private val service = Mockito.mock(RestroomService::class.java)
    private val remoteDataSource = RestroomRemoteDataSource(service)
    private val mockRemoteDataSource = Mockito.spy(remoteDataSource)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)
        Mockito.`when`(db.restroomDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = RestroomRepository(dao, remoteDataSource)
    }

    @Test
    fun loadRestroomsFromNetwork() {
        runBlocking {
            repository.restrooms

            Mockito.verify(dao, Mockito.never()).getRestrooms()
            Mockito.verifyZeroInteractions(dao)
        }
    }
}