package com.refactorme.demo.ui.di

import com.refactorme.demo.ui.CountriesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CountriesViewModel(get(), get()) }
}