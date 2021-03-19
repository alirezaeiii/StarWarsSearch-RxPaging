package com.android.sample.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.android.sample.common.paging.Listing
import com.android.sample.common.paging.NetworkState
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class BasePagingViewModel<T>(app: Application): AndroidViewModel(app) {

    // thread pool used for network requests
    protected val networkIO: ExecutorService = Executors.newFixedThreadPool(5)

    protected val compositeDisposable = CompositeDisposable()

    protected abstract val repoResult: LiveData<Listing<T>>

    val items: LiveData<PagedList<T>> by lazy { switchMap(repoResult) { it.pagedList } }
    val networkState: LiveData<NetworkState> by lazy { switchMap(repoResult) { it.networkState } }

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
        compositeDisposable.clear()
    }
}