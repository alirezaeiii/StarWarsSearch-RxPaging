package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.commons.R
import com.android.sample.commons.base.BasePageKeyedItemDataSource
import com.android.sample.commons.extension.isNetworkAvailable
import com.android.sample.commons.paging.NetworkState
import com.android.sample.commons.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.model.PeopleWrapper
import com.android.sample.core.model.Person
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
) : BasePageKeyedItemDataSource<Person, PeopleWrapper>(
    schedulerProvider, retryExecutor
) {

    override fun fetchItems(page: Int): Observable<PeopleWrapper> =
        composeObservable { useCase(query, page) }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Person>) {
        _networkState.postValue(NetworkState.LOADING)

        if (context.isNetworkAvailable()) {
            fetchItems(params.key).subscribe({
                _networkState.postValue(NetworkState.LOADED)
                //clear retry since last request succeeded
                retry = null
                if (it.next != null) {
                    callback.onResult(it.wrapper, params.key + 1)
                }
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
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Person>,
    ) {
        _networkState.postValue(NetworkState.LOADING)

        if (context.isNetworkAvailable()) {
            fetchItems(1).subscribe({
                _networkState.postValue(NetworkState.LOADED)
                if (it.next != null) {
                    callback.onResult(it.wrapper, null, 2)
                }
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
}