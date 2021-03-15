package com.android.sample.commons.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.sample.commons.R
import com.android.sample.commons.extension.isNetworkAvailable
import com.android.sample.commons.paging.NetworkState
import com.android.sample.commons.util.schedulers.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor


abstract class BasePageKeyedItemDataSource<T>(
    private val compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    private val retryExecutor: Executor,
    private val context: Context
) : PageKeyedDataSource<Int, T>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    protected abstract fun fetchItems(page: Int): Observable<List<T>>

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        _networkState.postValue(NetworkState.LOADING)

        if (context.isNetworkAvailable()) {
            composeObservable { fetchItems(params.key) }.subscribe({
                _networkState.postValue(NetworkState.LOADED)
                //clear retry since last request succeeded
                retry = null
                callback.onResult(it, params.key + 1)
            }) {
                retry = {
                    loadAfter(params, callback)
                }
                val error = NetworkState.error(context.getString(R.string.failed_loading_msg))
                _networkState.postValue(error)
            }.also { compositeDisposable.add(it) }
        } else {
            retry = {
                loadAfter(params, callback)
            }
            val error = NetworkState.error(context.getString(R.string.failed_network_msg))
            _networkState.postValue(error)
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        _networkState.postValue(NetworkState.LOADING)

        if (context.isNetworkAvailable()) {
            composeObservable { fetchItems(1) }
                .subscribe({
                    _networkState.postValue(NetworkState.LOADED)
                    callback.onResult(it, null, 2)
                }) {
                    retry = {
                        loadInitial(params, callback)
                    }
                    val error = NetworkState.error(context.getString(R.string.failed_loading_msg))
                    _networkState.postValue(error)
                }.also { compositeDisposable.add(it) }
        } else {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(context.getString(R.string.failed_network_msg))
            _networkState.postValue(error)
        }
    }

    private inline fun <T> composeObservable(task: () -> Observable<T>): Observable<T> = task()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
}