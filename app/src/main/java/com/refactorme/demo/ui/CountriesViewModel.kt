package com.refactorme.demo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refactorme.demo.data.net.NetResult
import com.refactorme.demo.domain.interactors.CountriesUseCase
import com.refactorme.demo.ui.entities.ItemCountry
import com.refactorme.demo.ui.mappers.ItemCountryMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val countriesUseCase: CountriesUseCase,
    private val itemCountryMapper: ItemCountryMapper,
    private val dispatcher : CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    val countriesList = MutableLiveData<List<ItemCountry>>(listOf())
    val errLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val loading = MutableLiveData(false)

    fun loadCountries() {
        viewModelScope.launch(dispatcher) {
            loading.postValue(true)
            when (val result = countriesUseCase.getAllCountries()) {
                is NetResult.Success -> {
                    val countries = itemCountryMapper.mapList(result.data)
                    countriesList.postValue(countries)
                }
                is NetResult.Error -> {
                    errLiveData.postValue(result.exception)
                }
            }
            loading.postValue(false)
        }
    }
}