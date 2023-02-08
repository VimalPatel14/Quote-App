package com.vimal.quote.helpers

/**
 * Created by Vimal on Feb-2023.
 */

import com.vimal.quote.model.Quote

object ValidationUtil {

    fun validateQuote(quote: Quote) : Boolean {
        if (quote.text.isNotEmpty() && quote.author.isNotEmpty()) {
            return true
        }
        return false
    }
}