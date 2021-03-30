package com.android.sample.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract class BaseDataSourceFactory<T, R> : DataSource.Factory<Int, T>() {

    private val _sourceLiveData = MutableLiveData<BasePageKeyedDataSource<T, R>>()
    val sourceLiveData: LiveData<BasePageKeyedDataSource<T, R>>
        get() = _sourceLiveData

    protected abstract fun getDataSource(): BasePageKeyedDataSource<T, R>

    override fun create(): DataSource<Int, T> {
        val source = getDataSource()
        _sourceLiveData.postValue(source)
        return source
    }
}