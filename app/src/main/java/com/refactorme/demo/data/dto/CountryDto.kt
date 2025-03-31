package com.refactorme.demo.data.dto

data class Country(
    val name: CountryName,
    val capital: List<String>,
    val latlng: List<Double>,
    val flag: String?,
    val languages: Map<String, String>
)

data class CountryName(
    val common: String,
    val official: String
)