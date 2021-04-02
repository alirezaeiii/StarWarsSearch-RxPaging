package com.android.sample.common.paging

import com.android.sample.common.util.schedulers.BaseSchedulerProvider

interface PageKeyRepository<T> {

    fun getItems(scheduler: BaseSchedulerProvider): Listing<T>
}