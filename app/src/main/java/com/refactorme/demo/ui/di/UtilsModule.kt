package com.refactorme.demo.ui.di

import com.refactorme.demo.ui.mappers.ItemCountryMapper
import org.koin.dsl.module

val utilsModule = module {
    single { ItemCountryMapper() }
}