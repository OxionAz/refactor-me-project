package com.refactorme.demo

import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesApi {

    @GET("v3.1/all")
    suspend fun getAllCountries(@Query("fields") fiels: List<String>): List<Country>
}