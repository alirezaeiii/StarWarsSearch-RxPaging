package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.commons.base.BaseDataSourceFactory
import com.android.sample.commons.base.BasePageKeyedItemDataSource
import com.android.sample.commons.util.schedulers.SchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.model.Person
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

class SearchDataSourceFactory(
    private val useCase: SearchPeopleUseCase,
    private val query: String,
    private val compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    private val retryExecutor: Executor,
    private val context: Context
) : BaseDataSourceFactory<Person>() {

    override fun getDataSource(): BasePageKeyedItemDataSource<Person> =
        SearchPageKeyedDataSource(useCase = useCase,
            query = query,
            compositeDisposable = compositeDisposable,
            schedulerProvider = schedulerProvider,
            retryExecutor = retryExecutor,
            context = context)
}