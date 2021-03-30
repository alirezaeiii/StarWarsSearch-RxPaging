package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.common.base.BaseDataSourceFactory
import com.android.sample.common.base.BasePageKeyedDataSource
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.response.PeopleWrapper
import com.android.sample.core.response.Character
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

class SearchDataSourceFactory(
        private val searchPeopleUseCase: SearchPeopleUseCase,
        private val query: String,
        private val compositeDisposable: CompositeDisposable,
        private val schedulerProvider: BaseSchedulerProvider,
        private val retryExecutor: Executor,
        private val context: Context,
) : BaseDataSourceFactory<Character, PeopleWrapper>() {

    override fun getDataSource(): BasePageKeyedDataSource<Character, PeopleWrapper> =
            SearchPageKeyedDataSource(searchPeopleUseCase = searchPeopleUseCase,
                    query = query,
                    compositeDisposable = compositeDisposable,
                    schedulerProvider = schedulerProvider,
                    retryExecutor = retryExecutor,
                    context = context)
}