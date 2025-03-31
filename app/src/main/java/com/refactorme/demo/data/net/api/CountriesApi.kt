package com.refactorme.demo.data.net.api

import com.refactorme.demo.data.dto.Country
import com.refactorme.demo.data.net.NetConstants.ALL_COUNTRIES_PATH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesApi {

    @GET(ALL_COUNTRIES_PATH)
    suspend fun getAllCountries(@Query("fields") fields: String): Response<List<Country>>
}