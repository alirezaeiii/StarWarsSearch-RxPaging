package com.android.sample.common.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

object DisposableManager {

    private val compositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }
}