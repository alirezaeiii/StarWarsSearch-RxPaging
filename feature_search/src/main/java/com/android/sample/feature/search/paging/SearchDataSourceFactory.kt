package com.android.sample.feature.search.paging

import android.content.Context
import com.android.sample.common.base.BaseDataSourceFactory
import com.android.sample.common.base.BasePageKeyedDataSource
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.response.Character
import com.android.sample.core.response.CharacterWrapper

class SearchDataSourceFactory(
        private val searchPeopleUseCase: SearchPeopleUseCase,
        private val query: String,
        private val schedulerProvider: BaseSchedulerProvider,
        private val context: Context,
) : BaseDataSourceFactory<Character, CharacterWrapper>() {

    override fun getDataSource(): BasePageKeyedDataSource<Character, CharacterWrapper> =
            SearchPageKeyedDataSource(searchPeopleUseCase = searchPeopleUseCase,
                    query = query,
                    schedulerProvider = schedulerProvider,
                    context = context)
}