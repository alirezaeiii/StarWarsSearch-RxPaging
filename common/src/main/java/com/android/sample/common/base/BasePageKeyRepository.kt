package com.android.sample.common.base

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.android.sample.common.paging.Listing
import com.android.sample.common.paging.PageKeyRepository
import com.android.sample.common.util.DisposableManager
import com.android.sample.common.util.schedulers.BaseSchedulerProvider

abstract class BasePageKeyRepository<T, R>(
        private val scheduler: BaseSchedulerProvider,
) : PageKeyRepository<T> {

    protected abstract val sourceFactory: BaseDataSourceFactory<T, R>

    private val pagedList = MutableLiveData<PagedList<T>>()

    @MainThread
    override fun getItems(): Listing<T> {

        val rxPagedList = RxPagedListBuilder(sourceFactory, PAGE_SIZE)
                .setFetchScheduler(scheduler.io()).buildObservable()

        val networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.networkState
        }

        rxPagedList.subscribeOn(scheduler.io()).subscribe {
            pagedList.postValue(it)
            }.also { DisposableManager.add(it) }

        return Listing(
                pagedList = pagedList,
                networkState = networkState,
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                }
        )
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}