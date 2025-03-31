package com.refactorme.demo.domain.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.refactorme.demo.data.net.NetConstants.ALL_COUNTRIES_DEF_FIELDS
import com.refactorme.demo.data.net.NetResult
import com.refactorme.demo.data.repository.CountriesRepository
import com.refactorme.demo.data.repository.mockedCountriesList
import com.refactorme.demo.utils.MainDispatcherRule
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
import org.mockito.kotlin.times

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CountriesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: CountriesRepository

    private lateinit var useCase: CountriesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        useCase = CountriesUseCase(repository)
    }

    @Test
    fun `test get all countries filter fields`() = runTest {
        // GIVEN
        val emptyFields = emptyList<String>()
        val customFields = listOf("name","capital")
        val successNetResult = NetResult.Success(mockedCountriesList)
        Mockito.`when`(repository.getAllCountries(Mockito.anyString())).thenReturn(successNetResult)

        // WHEN
        useCase.getAllCountries(ALL_COUNTRIES_DEF_FIELDS)
        useCase.getAllCountries(emptyFields)
        useCase.getAllCountries(customFields)

        // THEN
        with(inOrder(repository)) {
            verify(repository, times(2)).getAllCountries(ALL_COUNTRIES_DEF_FIELDS)
            verify(repository).getAllCountries(customFields.joinToString(","))
        }
    }
}