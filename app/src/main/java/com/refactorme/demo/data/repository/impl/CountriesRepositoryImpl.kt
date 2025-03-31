package com.refactorme.demo.data.repository.impl

import com.refactorme.demo.data.dto.Country
import com.refactorme.demo.data.net.NetResult
import com.refactorme.demo.data.net.api.CountriesApi
import com.refactorme.demo.data.repository.CountriesRepository

class CountriesRepositoryImpl(
    private val api: CountriesApi
) : CountriesRepository {
    override suspend fun getAllCountries(fields: String): NetResult<List<Country>> = try {
        val response = api.getAllCountries(fields)
        if (response.isSuccessful) {
            NetResult.Success(response.body()!!)
        } else {
            NetResult.Error(Exception(response.message()))
        }
    } catch (e: Exception) {
        NetResult.Error(e)
    }
}