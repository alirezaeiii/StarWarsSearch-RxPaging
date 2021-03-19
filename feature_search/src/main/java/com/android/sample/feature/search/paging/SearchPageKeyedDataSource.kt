package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.common.base.BasePageKeyedItemDataSource
import com.android.sample.common.paging.NetworkState
import com.android.sample.common.util.NetworkException
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
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
        context: Context,
) : BasePageKeyedItemDataSource<Character, PeopleWrapper>(
        schedulerProvider, retryExecutor, context) {

    private var isNext = true

    override fun fetchItems(page: Int): Observable<PeopleWrapper> =
            composeObservable { useCase(query, page) }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        if (isNext) {
            _networkState.postValue(NetworkState.LOADING)
            isNetworkAvailable.flatMap { fetchItems(it, params.key) }.subscribe({
                _networkState.postValue(NetworkState.LOADED)
                //clear retry since last request succeeded
                retry = null
                if (it.next == null) {
                    isNext = false
                }
                callback.onResult(it.wrapper, params.key + 1)
            }) {
                retry = {
                    loadAfter(params, callback)
                }
                initError(it)
            }.also { compositeDisposable.add(it) }
        }
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>,
    ) {
        _networkState.postValue(NetworkState.LOADING)

        isNetworkAvailable.flatMap { fetchItems(it, 1) }.subscribe({
            _networkState.postValue(NetworkState.LOADED)
            if (it.next == null) {
                isNext = false
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

    private fun fetchItems(isNetworkAvailable: Boolean, page: Int): Observable<PeopleWrapper> {
        return if (isNetworkAvailable) {
            fetchItems(page)
        } else {
            Observable.error(NetworkException())
        }
    }
}