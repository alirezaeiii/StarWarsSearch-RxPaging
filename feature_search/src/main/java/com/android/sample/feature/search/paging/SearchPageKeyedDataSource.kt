package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.commons.R
import com.android.sample.commons.base.BasePageKeyedItemDataSource
import com.android.sample.commons.extension.isNetworkAvailable
import com.android.sample.commons.paging.NetworkState
import com.android.sample.commons.util.NetworkException
import com.android.sample.commons.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.response.Character
import com.android.sample.core.response.PeopleWrapper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

class SearchPageKeyedDataSource(
        private val useCase: SearchPeopleUseCase,
        private val query: String,
        private val compositeDisposable: CompositeDisposable,
        schedulerProvider: BaseSchedulerProvider,
        retryExecutor: Executor,
        private val context: Context,
) : BasePageKeyedItemDataSource<Character, PeopleWrapper>(
        schedulerProvider, retryExecutor
) {

    private var flagToRetry = true

    private val isNetworkAvailable: Observable<Boolean> =
            Observable.fromCallable { context.isNetworkAvailable() }

    override fun fetchItems(page: Int): Observable<PeopleWrapper> =
            composeObservable { useCase(query, page) }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        _networkState.postValue(NetworkState.LOADING)

        isNetworkAvailable.flatMap { fetchItems(it, params.key) }.subscribe({
            _networkState.postValue(NetworkState.LOADED)
            //clear retry since last request succeeded
            retry = null
            if (it.next == null) {
                flagToRetry = false
            }
            callback.onResult(it.wrapper, params.key + 1)
        }) {
            if (flagToRetry) {
                retry = {
                    loadAfter(params, callback)
                }
                initError(it)
            } else {
                _networkState.postValue(NetworkState.LOADED)
            }
        }.also { compositeDisposable.add(it) }
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>,
    ) {
        _networkState.postValue(NetworkState.LOADING)

        isNetworkAvailable.flatMap { fetchItems(it, 1) }.subscribe({
            _networkState.postValue(NetworkState.LOADED)
            if (it.next == null) {
                flagToRetry = false
            }
            callback.onResult(it.wrapper, null, 2)
        }) {
            retry = {
                loadInitial(params, callback)
            }
            initError(it)
        }.also { compositeDisposable.add(it) }
    }

    // ============================================================================================
    //  Private helper methods
    // ============================================================================================

    private fun initError(throwable: Throwable) {
        if (throwable is NetworkException) {
            val error = NetworkState.error(context.getString(R.string.failed_network_msg))
            _networkState.postValue(error)
        } else {
            val error = NetworkState.error(context.getString(R.string.failed_loading_msg))
            _networkState.postValue(error)
        }
    }

    private fun fetchItems(isNetworkAvailable: Boolean, page: Int): Observable<PeopleWrapper> {
        return if (isNetworkAvailable) {
            fetchItems(page)
        } else {
            Observable.error(NetworkException())
        }
    }
}