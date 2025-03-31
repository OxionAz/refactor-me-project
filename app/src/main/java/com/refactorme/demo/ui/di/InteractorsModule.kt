package com.refactorme.demo.ui.di

import com.refactorme.demo.domain.interactors.CountriesUseCase
import org.koin.dsl.module

val interactorsModule = module {
    factory { CountriesUseCase(get()) }
}