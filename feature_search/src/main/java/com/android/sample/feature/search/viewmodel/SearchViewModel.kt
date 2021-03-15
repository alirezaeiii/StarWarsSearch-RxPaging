package com.android.sample.feature.search.viewmodel

import com.android.sample.commons.base.BaseViewModel
import com.android.sample.commons.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.model.Person
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val useCase: SearchPeopleUseCase,
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel<List<Person>>(schedulerProvider) {

    fun searchPeople(query: String) {
        super.sendRequest(useCase(query))
    }
}