package com.android.sample.common.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.sample.common.R
import com.android.sample.common.extension.isNetworkAvailable
import com.android.sample.common.paging.NetworkState
import com.android.sample.common.util.NetworkException
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class BasePageKeyedDataSource<T, K>(
    private val schedulerProvider: BaseSchedulerProvider,
    private val context: Context,
) : PageKeyedDataSource<Int, T>() {

    // thread pool used for network requests
    private val networkIO: ExecutorService = Executors.newFixedThreadPool(5)

    // keep a function reference for the retry event
    protected var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    protected val mutableNetworkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = mutableNetworkState

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            networkIO.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // ignored, since we only ever append to our initial load
    }

    protected abstract fun fetchItems(page: Int): Observable<K>

    protected fun loadItems(page: Int): Observable<K> =
        Observable.fromCallable { context.isNetworkAvailable() }.flatMap {
            return@flatMap if (it) fetchItems(page)
            else Observable.error(NetworkException())
        }

    protected fun setErrorMsg(throwable: Throwable) {
        mutableNetworkState.postValue(
            NetworkState.error(
                context.getString(
                    if (throwable is NetworkException) R.string.failed_network_msg
                    else R.string.failed_loading_msg
                )
            )
        )
    }
}