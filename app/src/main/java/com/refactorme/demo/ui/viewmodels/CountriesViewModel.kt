package com.refactorme.demo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refactorme.demo.data.net.NetResult
import com.refactorme.demo.domain.interactors.CountriesUseCase
import com.refactorme.demo.ui.mappers.ItemCountryMapper
import com.refactorme.demo.ui.screens.CountriesIntent
import com.refactorme.demo.ui.screens.CountriesScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountriesViewModel(
    private val countriesUseCase: CountriesUseCase,
    private val itemCountryMapper: ItemCountryMapper,
    private val ioDispatcher : CoroutineDispatcher,
    private val defDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _state = MutableStateFlow(CountriesScreenState())
    val state: StateFlow<CountriesScreenState> = _state

    fun handleIntent(intent: CountriesIntent) = when (intent) {
        is CountriesIntent.LoadCountries -> loadCountries()
    }

    private fun loadCountries() {
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch(ioDispatcher) {
            when (val result = countriesUseCase.getAllCountries()) {
                is NetResult.Success -> {
                    val countries = withContext(defDispatcher) {
                        itemCountryMapper.mapList(result.data)
                    }
                    _state.value = CountriesScreenState(
                        isLoading = false,
                        countries = countries
                    )
                }
                is NetResult.Error -> {
                    _state.value = CountriesScreenState(
                        isLoading = false,
                        error = result.exception.message
                    )
                }
            }
        }
    }
}