package com.applications.currencyconverter
import org.junit.Test
import org.junit.Assert

class CurrencyConversionTest {
    @Test
    fun testConversion() {
        val exchangeRate = 0.77
        val amount = 100.0
        val expectedResult = 77.0

        val result = convertCurrency(amount, exchangeRate)

        Assert.assertEquals(expectedResult, result, 0.01)
    }

    private fun convertCurrency(amount: Double, exchangeRate: Double): Double {
        return amount * exchangeRate
    }
}





