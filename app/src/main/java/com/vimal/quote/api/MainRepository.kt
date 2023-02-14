package com.vimal.quote.api

/**
 * Created by Vimal on Feb-2023.
 */

import com.vimal.quote.model.Quote
import javax.inject.Inject

class MainRepository @Inject constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllQuotes(): NetworkState<List<Quote>> {
        val response = retrofitService.getAllQuotes()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}