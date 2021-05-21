package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.common.base.BaseDataSourceFactory
import com.android.sample.common.base.BasePageKeyedDataSource
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.response.Character
import com.android.sample.core.response.CharacterWrapper

class SearchDataSourceFactory(
    searchPeopleUseCase: SearchPeopleUseCase,
    query: String,
    schedulerProvider: BaseSchedulerProvider,
    context: Context,
) : BaseDataSourceFactory<Character, CharacterWrapper>() {

    override val dataSource: BasePageKeyedDataSource<Character, CharacterWrapper> =
        SearchPageKeyedDataSource(
            searchPeopleUseCase = searchPeopleUseCase,
            query = query,
            schedulerProvider = schedulerProvider,
            context = context
        )
}