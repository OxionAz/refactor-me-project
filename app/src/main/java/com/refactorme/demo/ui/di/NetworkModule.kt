package com.refactorme.demo.ui.di

import com.refactorme.demo.data.net.NetClientHelper
import com.refactorme.demo.data.net.api.CountriesApi
import org.koin.dsl.module

val networkModule = module {
    single<CountriesApi> { NetClientHelper.getApiService() as CountriesApi }
}