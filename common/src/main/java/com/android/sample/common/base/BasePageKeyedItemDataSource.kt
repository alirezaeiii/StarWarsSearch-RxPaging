package com.android.sample.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.sample.common.paging.NetworkState
import com.android.sample.common.util.EspressoIdlingResource
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import java.util.concurrent.Executor


abstract class BasePageKeyedItemDataSource<T, R>(
        protected val schedulerProvider: BaseSchedulerProvider,
        private val retryExecutor: Executor,
) : PageKeyedDataSource<Int, T>() {

    // keep a function reference for the retry event
    protected var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    protected val _networkState = MutableLiveData<NetworkState>()
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

    protected abstract fun fetchItems(page: Int): Observable<R>

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // ignored, since we only ever append to our initial load
    }

    protected inline fun <T> composeObservable(task: () -> Observable<T>): Observable<T> = task()
            .doOnSubscribe { EspressoIdlingResource.increment() } // App is busy until further notice
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doFinally { EspressoIdlingResource.decrement() } // Set app as idle.
}