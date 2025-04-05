package com.refactorme.demo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.refactorme.demo.data.net.NetResult
import com.refactorme.demo.data.repository.mockedCountriesList
import com.refactorme.demo.domain.interactors.CountriesUseCase
import com.refactorme.demo.ui.mappers.ItemCountryMapper
import com.refactorme.demo.ui.screens.CountriesIntent
import com.refactorme.demo.ui.screens.CountriesScreenState
import com.refactorme.demo.ui.viewmodels.CountriesViewModel
import com.refactorme.demo.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CountriesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: CountriesUseCase

    private lateinit var viewModel: CountriesViewModel

    private val initialState = CountriesScreenState()

    private val loadingState = CountriesScreenState(isLoading = true)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = CountriesViewModel(
            useCase,
            ItemCountryMapper(),
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun `test load countries SUCCESS`() = runTest {
        // GIVEN
        val mapper = ItemCountryMapper()
        val expectedState = CountriesScreenState(countries = mapper.mapList(mockedCountriesList))
        val stateResults = mutableListOf<CountriesScreenState>()
        Mockito.`when`(useCase.getAllCountries()).thenReturn(NetResult.Success(mockedCountriesList))
        backgroundScope.launch(mainDispatcherRule.testDispatcher) {
            viewModel.state.collect { stateResults.add(it) }
        }

        // WHEN
        viewModel.handleIntent(CountriesIntent.LoadCountries)

        // THEN
        Mockito.verify(useCase).getAllCountries()
        assertEquals(listOf(initialState, loadingState, expectedState), stateResults)
    }

    @Test
    fun `test load countries ERROR`() = runTest {
        // GIVEN
        val error = Exception("test")
        val expectedState = CountriesScreenState(error = error.message)
        val stateResults = mutableListOf<CountriesScreenState>()
        Mockito.`when`(useCase.getAllCountries()).thenReturn(NetResult.Error(error))
        backgroundScope.launch(mainDispatcherRule.testDispatcher) {
            viewModel.state.collect { stateResults.add(it) }
        }

        // WHEN
        viewModel.handleIntent(CountriesIntent.LoadCountries)

        // THEN
        Mockito.verify(useCase).getAllCountries()
        assertEquals(listOf(initialState, loadingState, expectedState), stateResults)
    }
}