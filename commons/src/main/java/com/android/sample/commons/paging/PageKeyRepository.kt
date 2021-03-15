package com.android.sample.commons.paging

import java.util.concurrent.Executor

interface PageKeyRepository<T> {

    fun getItems(networkExecutor: Executor): Listing<T>
}