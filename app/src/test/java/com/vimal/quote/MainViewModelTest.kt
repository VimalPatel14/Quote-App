package com.vimal.quote

/**
 * Created by Vimal on Feb-2023.
 */

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vimal.quote.api.MainRepository
import com.vimal.quote.api.NetworkState
import com.vimal.quote.model.Quote
import com.vimal.quote.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var mainViewModel: MainViewModel
    lateinit var mainRepository: MainRepository

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainRepository = mock(MainRepository::class.java)
        mainViewModel = MainViewModel(mainRepository)
    }

    @Test
    fun getAllQuoteTest() {
        runBlocking {
            Mockito.`when`(mainRepository.getAllQuotes())
                .thenReturn(NetworkState.Success(listOf<Quote>(Quote("movie", "new"))))
            mainViewModel.getAllQuotes()
            val result = mainViewModel.quoteList.getOrAwaitValue()
            assertEquals(listOf<Quote>(Quote("movie", "new")), result)
        }
    }

    @Test
    fun `empty quote list test`() {
        runBlocking {
            Mockito.`when`(mainRepository.getAllQuotes())
                .thenReturn(NetworkState.Success(listOf<Quote>()))
            mainViewModel.getAllQuotes()
            val result = mainViewModel.quoteList.getOrAwaitValue()
            assertEquals(listOf<Quote>(), result)
        }
    }
}