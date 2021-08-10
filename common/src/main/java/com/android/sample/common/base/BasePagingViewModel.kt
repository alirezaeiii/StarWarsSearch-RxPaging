package com.android.sample.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.android.sample.common.paging.Listing
import com.android.sample.common.paging.NetworkState
import com.android.sample.common.util.DisposableManager

abstract class BasePagingViewModel<T>(
    app: Application
) : AndroidViewModel(app) {

    protected abstract val repoResult: LiveData<Listing<T>>

    val networkState: LiveData<NetworkState> by lazy { switchMap(repoResult) { it.networkState } }
    val pagedList: LiveData<PagedList<T>> by lazy { switchMap(repoResult) { it.pagedList } }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all disposables;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        DisposableManager.clear()
    }
}