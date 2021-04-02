package com.android.sample.common.paging

interface PageKeyRepository<T> {

    fun getItems(): Listing<T>
}