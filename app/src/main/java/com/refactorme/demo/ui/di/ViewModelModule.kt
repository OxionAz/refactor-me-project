package com.refactorme.demo.ui.di

import com.refactorme.demo.ui.viewmodels.CountriesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CountriesViewModel(
            get(),
            get(),
            get(named("IODispatcher")),
            get(named("DefaultDispatcher"))
        )
    }
}