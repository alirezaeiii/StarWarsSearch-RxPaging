package com.android.sample.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import com.android.sample.common.paging.Listing
import com.android.sample.common.paging.NetworkState
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BasePagingViewModel<T>(
    app: Application,
    private val schedulerProvider: BaseSchedulerProvider
) : AndroidViewModel(app) {

    protected val compositeDisposable = CompositeDisposable()

    protected abstract val repoResult: LiveData<Listing<T>>

    private val _liveData = MutableLiveData<PagedList<T>>()
    val liveData: LiveData<PagedList<T>>
        get() = _liveData

    val networkState: LiveData<NetworkState> by lazy { switchMap(repoResult) { it.networkState } }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }

    fun showQuery() {
        repoResult.value?.pagedList?.subscribeOn(schedulerProvider.io())
            ?.observeOn(schedulerProvider.ui())
            ?.subscribe({
                _liveData.postValue(it)
            }) {
                Timber.e(it)
            }.also { disposable -> disposable?.let { compositeDisposable.add(it) } }
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