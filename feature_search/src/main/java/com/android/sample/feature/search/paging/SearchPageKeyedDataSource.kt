package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.commons.base.BasePageKeyedItemDataSource
import com.android.sample.commons.util.schedulers.SchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.model.Person
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

class SearchPageKeyedDataSource(
    private val useCase: SearchPeopleUseCase,
    private val query: String,
    compositeDisposable: CompositeDisposable,
    schedulerProvider: SchedulerProvider,
    retryExecutor: Executor,
    context: Context
) : BasePageKeyedItemDataSource<Person>(
    compositeDisposable,
    schedulerProvider,
    retryExecutor,
    context
) {

    override fun fetchItems(page: Int): Observable<List<Person>> =
        useCase(query, page)
}