package com.refactorme.demo.data.repository

import com.refactorme.demo.data.dto.Country
import com.refactorme.demo.data.net.NetResult

interface CountriesRepository {
    suspend fun getAllCountries(fields: String): NetResult<List<Country>>
}