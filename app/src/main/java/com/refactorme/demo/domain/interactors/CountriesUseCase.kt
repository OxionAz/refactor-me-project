package com.refactorme.demo.domain.interactors

import com.refactorme.demo.data.dto.Country
import com.refactorme.demo.data.net.NetConstants.ALL_COUNTRIES_DEF_FIELDS
import com.refactorme.demo.data.net.NetResult
import com.refactorme.demo.data.repository.CountriesRepository

class CountriesUseCase(
    private val repository: CountriesRepository
) {
    suspend fun getAllCountries(fields: String = ALL_COUNTRIES_DEF_FIELDS): NetResult<List<Country>> {
        return repository.getAllCountries(fields)
    }

    suspend fun getAllCountries(fields: List<String>): NetResult<List<Country>> {
        val checkedFields = if (fields.isNotEmpty()) {
            fields.joinToString(",")
        }  else {
            ALL_COUNTRIES_DEF_FIELDS
        }
        return repository.getAllCountries(checkedFields)
    }
}