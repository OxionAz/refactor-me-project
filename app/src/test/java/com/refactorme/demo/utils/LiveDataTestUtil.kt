package com.refactorme.demo.utils

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.mockito.Mockito.mock

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.testObserver(afterObserve: (observer: Observer<T>) -> Unit = {}) {
    val observer = mock(Observer::class.java) as Observer<T>

    try {
        this.observeForever(observer)
        afterObserve.invoke(observer)
    } finally {
        this.removeObserver(observer)
    }
}