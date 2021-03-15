package com.android.sample.commons.base

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.android.sample.commons.paging.Listing
import com.android.sample.commons.paging.PageKeyRepository
import java.util.concurrent.Executor

abstract class BasePageKeyRepository<T, R> : PageKeyRepository<T> {

    protected abstract fun getSourceFactory(retryExecutor: Executor): BaseDataSourceFactory<T, R>

    @MainThread
    override fun getItems(networkExecutor: Executor): Listing<T> {

        val sourceFactory = getSourceFactory(networkExecutor)

        val livePagedList = LivePagedListBuilder(sourceFactory, PAGE_SIZE)
            // provide custom executor for network requests, otherwise it will default to
            // Arch Components' IO pool which is also used for disk access
            .setFetchExecutor(networkExecutor)
            .build()


        val networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.networkState
        }

        return Listing(
            pagedList = livePagedList,
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