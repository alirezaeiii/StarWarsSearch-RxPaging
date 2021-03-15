package com.android.sample.commons.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract class BaseDataSourceFactory<T, R> : DataSource.Factory<Int, T>() {

    private val _sourceLiveData = MutableLiveData<BasePageKeyedItemDataSource<T, R>>()
    val sourceLiveData: LiveData<BasePageKeyedItemDataSource<T, R>>
        get() = _sourceLiveData

    protected abstract fun getDataSource(): BasePageKeyedItemDataSource<T, R>

    override fun create(): DataSource<Int, T> {
        val source = getDataSource()
        _sourceLiveData.postValue(source)
        return source
    }
}