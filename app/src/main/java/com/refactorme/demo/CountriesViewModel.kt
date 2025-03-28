package com.refactorme.demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountriesViewModel: ViewModel() {

    private val countryApi: CountriesApi = Dependencies.api

    val countriesList = MutableLiveData<List<Country>>(listOf())

    fun loadCountries(fields: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fieldsList = fields.split(",").map(String::trim)
            val countries = countryApi.getAllCountries(fieldsList)
            withContext(Dispatchers.Main) {
                countriesList.value = countries
            }
        }
    }
}