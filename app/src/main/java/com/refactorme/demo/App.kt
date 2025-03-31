package com.refactorme.demo

import android.app.Application
import com.refactorme.demo.ui.di.interactorsModule
import com.refactorme.demo.ui.di.networkModule
import com.refactorme.demo.ui.di.repositoryModule
import com.refactorme.demo.ui.di.utilsModule
import com.refactorme.demo.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(
                repositoryModule,
                interactorsModule,
                networkModule,
                utilsModule,
                viewModelModule
            )
        }
    }
}