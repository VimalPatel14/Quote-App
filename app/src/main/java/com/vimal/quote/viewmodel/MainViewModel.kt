package com.vimal.quote.viewmodel

/**
 * Created by Vimal on Feb-2023.
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimal.quote.api.MainRepository
import com.vimal.quote.api.NetworkState
import com.vimal.quote.model.Quote
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val quoteList = MutableLiveData<List<Quote>>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllQuotes() {
//        Log.d("Thread Outside", Thread.currentThread().name)
        viewModelScope.launch {
//            Log.d("Thread Inside", Thread.currentThread().name)
            when (val response = mainRepository.getAllQuotes()) {
                is NetworkState.Success -> {
                    quoteList.postValue(response.data)
                }
                is NetworkState.Error -> {
                    if (response.response.code() == 401) {
                        //quoteList.postValue(NetworkState.Error())
                    } else {
                        //quoteList.postValue(NetworkState.Error)
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}