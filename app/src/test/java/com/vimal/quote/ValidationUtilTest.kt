package com.vimal.quote

/**
 * Created by Vimal on Feb-2023.
 */

import com.vimal.quote.helpers.ValidationUtil
import com.vimal.quote.model.Quote
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationUtilTest {

    @Test
    fun validateQuoteTest() {
        val quote = Quote("test","testUrl")
        assertEquals(true, ValidationUtil.validateQuote(quote))
    }

    @Test
    fun validateQuoteEmptyTest() {
        val quote = Quote("","main")
        assertEquals(false, ValidationUtil.validateQuote(quote))
    }

}