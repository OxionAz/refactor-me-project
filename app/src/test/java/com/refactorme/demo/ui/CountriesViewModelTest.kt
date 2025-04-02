package com.refactorme.demo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.refactorme.demo.data.net.NetResult
import com.refactorme.demo.data.repository.mockedCountriesList
import com.refactorme.demo.domain.interactors.CountriesUseCase
import com.refactorme.demo.ui.entities.ItemCountry
import com.refactorme.demo.ui.mappers.ItemCountryMapper
import com.refactorme.demo.ui.viewmodels.CountriesViewModel
import com.refactorme.demo.utils.MainDispatcherRule
import com.refactorme.demo.utils.testObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.inOrder
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

    private lateinit var expectedAllCountriesList: List<ItemCountry>

    private lateinit var viewModel: CountriesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        val mapper = ItemCountryMapper()

        expectedAllCountriesList = mapper.mapList(mockedCountriesList)

        viewModel = CountriesViewModel(useCase, mapper, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun `test load countries SUCCESS`() = runTest {
        // GIVEN
        Mockito.`when`(useCase.getAllCountries()).thenReturn(NetResult.Success(mockedCountriesList))

        // WHEN
        viewModel.loadCountries()

        // THEN
        Mockito.verify(useCase).getAllCountries()
        viewModel.loading.testObserver {
            with(inOrder(it).verify(it)) {
                onChanged(false)
                onChanged(true)
                onChanged(false)
            }
        }
        assert(viewModel.countriesList.value == expectedAllCountriesList)
        assert(viewModel.errLiveData.value == null)
    }

    @Test
    fun `test load countries ERROR`() = runTest {
        // GIVEN
        val error = Exception("test")
        Mockito.`when`(useCase.getAllCountries()).thenReturn(NetResult.Error(error))

        // WHEN
        viewModel.loadCountries()

        // THEN
        Mockito.verify(useCase).getAllCountries()
        viewModel.loading.testObserver {
            with(inOrder(it).verify(it)) {
                onChanged(false)
                onChanged(true)
                onChanged(false)
            }
        }
        assert(viewModel.countriesList.value == emptyList<ItemCountry>())
        assert(viewModel.errLiveData.value == error)
    }
}