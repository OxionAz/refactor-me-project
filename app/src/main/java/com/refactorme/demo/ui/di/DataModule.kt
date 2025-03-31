package com.refactorme.demo.ui.di

import com.refactorme.demo.data.repository.CountriesRepository
import com.refactorme.demo.data.repository.impl.CountriesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<CountriesRepository> { CountriesRepositoryImpl(get()) }
}