package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.common.base.BasePageKeyedDataSource
import com.android.sample.common.paging.NetworkState
import com.android.sample.common.util.DisposableManager
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.response.Character
import com.android.sample.core.response.CharacterWrapper
import io.reactivex.Observable

class SearchPageKeyedDataSource(
        private val searchPeopleUseCase: SearchPeopleUseCase,
        private val query: String,
        schedulerProvider: BaseSchedulerProvider,
        context: Context,
) : BasePageKeyedDataSource<Character, CharacterWrapper>(
        schedulerProvider, context) {

    private var isNext = true

    override fun fetchObservableItem(page: Int): Observable<CharacterWrapper> =
            searchPeopleUseCase(query, page)

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        if (isNext) {
            mutableNetworkState.postValue(NetworkState.LOADING)
            fetchItems(params.key).subscribe({
                mutableNetworkState.postValue(NetworkState.LOADED)
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
                setErrorMsg(it)
            }.also { DisposableManager.getInstance().add(it) }
        }
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>,
    ) {
        mutableNetworkState.postValue(NetworkState.LOADING)

        fetchItems(1).subscribe({
            mutableNetworkState.postValue(NetworkState.LOADED)
            if (it.next == null) {
                isNext = false
            }
            callback.onResult(it.wrapper, null, 2)
        }) {
            retry = {
                loadInitial(params, callback)
            }
            setErrorMsg(it)
        }.also { DisposableManager.getInstance().add(it) }
    }
}