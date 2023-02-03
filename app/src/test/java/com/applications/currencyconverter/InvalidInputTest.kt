package com.applications.currencyconverter
import org.junit.Test
import org.junit.Assert

class InvalidInputTest {
    @Test
    fun testNegativeAmount() {
        val exchangeRate = 0.77
        val amount = -100.0
        val expectedResult = 0.0

        val result = convertCurrency(amount, exchangeRate)

        Assert.assertEquals(expectedResult, result, 0.01)
    }

    @Test
    fun testNonNumericAmount() {
        val exchangeRate = 0.77
        val amount = "not a number"
        val expectedResult = 0.0

        val result = convertCurrency(amount, exchangeRate)

        Assert.assertEquals(expectedResult, result, 0.01)
    }

    @Test
    fun testUnsupportedCurrency() {
        val exchangeRate = 0.77
        val amount = 100.0
        val currency = "XYZ"
        val expectedResult = 0.0

        val result = convertCurrency(amount, exchangeRate, currency)

        Assert.assertEquals(expectedResult, result, 0.01)
    }

    private fun convertCurrency(amount: Any, exchangeRate: Double, currency: String = "USD"): Double {
        val numericAmount = when (amount) {
            is Double -> amount
            is String -> {
                try {
                    amount.toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }
            }
            else -> 0.0
        }

        return if (numericAmount >= 0.0 && supportedCurrencies.contains(currency)) {
            numericAmount * exchangeRate
        } else {
            0.0
        }
    }

    private val supportedCurrencies = listOf("USD", "EUR", "GBP")
}
