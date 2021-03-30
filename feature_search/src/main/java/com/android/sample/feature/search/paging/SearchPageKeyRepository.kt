package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.common.base.BaseDataSourceFactory
import com.android.sample.common.base.BasePageKeyRepository
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.response.CharacterWrapper
import com.android.sample.core.response.Character
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

class SearchPageKeyRepository(
        private val searchPeopleUseCase: SearchPeopleUseCase,
        private val query: String,
        private val compositeDisposable: CompositeDisposable,
        private val schedulerProvider: BaseSchedulerProvider,
        private val context: Context,
) : BasePageKeyRepository<Character, CharacterWrapper>() {

    override fun getSourceFactory(retryExecutor: Executor): BaseDataSourceFactory<Character, CharacterWrapper> =
            SearchDataSourceFactory(searchPeopleUseCase = searchPeopleUseCase,
                    query = query,
                    compositeDisposable = compositeDisposable,
                    schedulerProvider = schedulerProvider,
                    retryExecutor = retryExecutor,
                    context = context
            )
}