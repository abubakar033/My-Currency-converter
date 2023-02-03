package com.applications.currencyconverter.models

data class LiveCurrencyRateDTO(
    val quotes: HashMap<String, Double>,
    val source: String,
    val success: Boolean,
    val timestamp: Int
)