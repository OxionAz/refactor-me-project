package com.refactorme.demo.ui.di

import com.refactorme.demo.ui.mappers.ItemCountryMapper
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val utilsModule = module {
    single(named("DefaultDispatcher")) { Dispatchers.Default }
    single(named("IODispatcher")) { Dispatchers.IO }
    single { ItemCountryMapper() }
}