package com.android.sample.common.paging

import java.util.concurrent.Executor

interface PageKeyRepository<T> {

    fun getItems(networkExecutor: Executor): Listing<T>
}