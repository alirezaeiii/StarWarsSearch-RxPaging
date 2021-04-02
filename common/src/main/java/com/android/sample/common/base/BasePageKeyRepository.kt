package com.android.sample.common.base

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.RxPagedListBuilder
import com.android.sample.common.paging.Listing
import com.android.sample.common.paging.PageKeyRepository
import com.android.sample.common.util.schedulers.BaseSchedulerProvider

abstract class BasePageKeyRepository<T, R>(
        private val scheduler: BaseSchedulerProvider,
) : PageKeyRepository<T> {

    protected abstract fun getSourceFactory(): BaseDataSourceFactory<T, R>

    @MainThread
    override fun getItems(): Listing<T> {

        val sourceFactory = getSourceFactory()

        val rxPagedList = RxPagedListBuilder(sourceFactory, PAGE_SIZE)
                .setFetchScheduler(scheduler.io()).setNotifyScheduler(scheduler.ui())
                .buildObservable()

        val networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.networkState
        }

        return Listing(
                pagedList = rxPagedList,
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