package com.refactorme.demo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.refactorme.demo.data.dto.Country
import com.refactorme.demo.data.dto.CountryName
import com.refactorme.demo.data.net.NetConstants.ALL_COUNTRIES_DEF_FIELDS
import com.refactorme.demo.data.net.NetResult
import com.refactorme.demo.data.net.api.CountriesApi
import com.refactorme.demo.data.repository.impl.CountriesRepositoryImpl
import com.refactorme.demo.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CountriesRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var api: CountriesApi

    private lateinit var repository: CountriesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        repository = CountriesRepositoryImpl(api)
    }

    @Test
    fun `test get all countries SUCCESS`() = runTest {
        // GIVEN
        val successResponse = Response.success(mockedCountriesList)
        val successNetResult = NetResult.Success(mockedCountriesList)
        Mockito.`when`(api.getAllCountries(ALL_COUNTRIES_DEF_FIELDS)).thenReturn(successResponse)

        // WHEN
        val netResult = repository.getAllCountries(ALL_COUNTRIES_DEF_FIELDS)

        // THEN
        Mockito.verify(api).getAllCountries(ALL_COUNTRIES_DEF_FIELDS)
        assert(netResult.toString() == successNetResult.toString())
    }

    @Test
    fun `test get all countries ERROR`() = runTest {
        // GIVEN
        val errorBody = "Response.error()"
        val errorResponse = Response.error<List<Country>>(
            HttpURLConnection.HTTP_INTERNAL_ERROR,
            errorBody.toResponseBody("text/plain".toMediaTypeOrNull())
        )
        val errorNetResult = NetResult.Error(Exception(errorBody))
        Mockito.`when`(api.getAllCountries(ALL_COUNTRIES_DEF_FIELDS)).thenReturn(errorResponse)

        // WHEN
        val netResult = repository.getAllCountries(ALL_COUNTRIES_DEF_FIELDS)

        // THEN
        Mockito.verify(api).getAllCountries(ALL_COUNTRIES_DEF_FIELDS)
        assert(netResult.toString() == errorNetResult.toString())
    }
}

val mockedCountriesList = listOf(
    Country(
        CountryName("South Georgia", "South Georgia and the South Sandwich Islands"),
        listOf("King Edward Point"),
        listOf(-54.5, -37.0),
        "🇬🇸",
        mapOf("eng" to "English")
    ),
    Country(
        CountryName("Switzerland", "Swiss Confederation"),
        listOf("Bern"),
        listOf(47.0, 8.0),
        "🇨🇭",
        mapOf("eng" to "English")
    )
)