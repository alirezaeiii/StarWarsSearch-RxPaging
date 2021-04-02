package com.android.sample.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.android.sample.common.paging.Listing
import com.android.sample.common.paging.NetworkState
import io.reactivex.disposables.CompositeDisposable

abstract class BasePagingViewModel<T>(app: Application) : AndroidViewModel(app) {

    protected val compositeDisposable = CompositeDisposable()

    protected abstract val repoResult: LiveData<Listing<T>>

    protected val mutableLiveData = MutableLiveData<PagedList<T>>()
    val liveData: LiveData<PagedList<T>>
        get() = mutableLiveData

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