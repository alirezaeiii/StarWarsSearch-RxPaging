package com.android.sample.common.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableManager private constructor() {

    private val compositeDisposable = CompositeDisposable()

    fun add(disposable : Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }

    companion object {
        private var instance: DisposableManager? = null

        fun getInstance(): DisposableManager = instance
                ?: DisposableManager().also { instance = it }
    }
}