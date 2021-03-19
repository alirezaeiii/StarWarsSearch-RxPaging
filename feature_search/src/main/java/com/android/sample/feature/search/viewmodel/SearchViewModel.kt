package com.android.sample.feature.search.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.sample.common.base.BasePagingViewModel
import com.android.sample.common.paging.Listing
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.response.Character
import com.android.sample.feature.search.paging.SearchPageKeyRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val useCase: SearchPeopleUseCase,
    private val schedulerProvider: BaseSchedulerProvider,
    private val app: Application
) : BasePagingViewModel<Character>(app) {

    private val query = MutableLiveData<String>()

    override val repoResult: LiveData<Listing<Character>> = Transformations.map(query) {
        SearchPageKeyRepository(
            useCase, it, compositeDisposable,
            schedulerProvider, app.applicationContext
        ).getItems(networkIO)
    }

    fun showQuery(query: String): Boolean {
        if (this.query.value == query) {
            return false
        }
        this.query.value = query
        return true
    }
}