package com.vimal.quote.viewmodel

/**
 * Created by Vimal on Feb-2023.
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vimal.quote.api.MainRepository
import com.vimal.quote.viewmodel.MainViewModel
import javax.inject.Inject

class MyViewModelFactory @Inject constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}