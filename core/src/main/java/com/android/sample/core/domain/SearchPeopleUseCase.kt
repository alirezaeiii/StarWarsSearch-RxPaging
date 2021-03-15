package com.android.sample.core.domain

import com.android.sample.core.model.Person
import com.android.sample.core.repository.SearchRepository
import io.reactivex.Single
import javax.inject.Inject

class SearchPeopleUseCase @Inject constructor(
    private val tasksRepository: SearchRepository
) {
    operator fun invoke(query: String): Single<List<Person>> = tasksRepository.searchPeople(query)
}