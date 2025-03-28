package com.refactorme.demo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Dependencies {

    val api = createApi()

    private fun createApi(): CountriesApi {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
}