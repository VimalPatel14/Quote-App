package com.vimal.quote

/**
 * Created by Vimal on Feb-2023.
 */

import com.vimal.quote.api.MainRepository
import com.vimal.quote.api.RetrofitService
import com.vimal.quote.model.Quote
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MainRepositoryTest {

    lateinit var mainRepository: MainRepository

    @Mock
    lateinit var apiService: RetrofitService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(apiService)
    }

    @Test
    fun `get all quote test`() {
        runBlocking {
            Mockito.`when`(apiService.getAllQuotes()).thenReturn(Response.success(listOf<Quote>()))
            val response = mainRepository.getAllQuotes()
            assertNotNull(response)
        }
    }
}