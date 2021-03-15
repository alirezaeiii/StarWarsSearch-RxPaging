package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.commons.base.BaseDataSourceFactory
import com.android.sample.commons.base.BasePageKeyRepository
import com.android.sample.commons.util.schedulers.SchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.model.Person
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

class SearchPageKeyRepository(
    private val useCase: SearchPeopleUseCase,
    private val query: String,
    private val compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    private val context: Context
) : BasePageKeyRepository<Person>() {

    override fun getSourceFactory(retryExecutor: Executor): BaseDataSourceFactory<Person> =
        SearchDataSourceFactory(
            useCase = useCase,
            query = query,
            compositeDisposable = compositeDisposable,
            schedulerProvider = schedulerProvider,
            retryExecutor = retryExecutor,
            context = context
        )
}