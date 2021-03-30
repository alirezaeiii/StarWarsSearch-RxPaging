package com.android.sample.core.domain

import com.android.sample.core.response.CharacterWrapper
import com.android.sample.core.repository.SearchRepository
import io.reactivex.Observable
import javax.inject.Inject

class SearchPeopleUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String, page: Int): Observable<CharacterWrapper> =
        searchRepository.searchPeople(query, page)
}