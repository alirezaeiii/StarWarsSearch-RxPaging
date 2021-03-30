package com.android.sample.core.repository

import com.android.sample.core.response.CharacterWrapper
import com.android.sample.core.network.StarWarsService
import io.reactivex.Observable
import javax.inject.Inject

class SearchRepository @Inject constructor(private val service: StarWarsService) {

    fun searchPeople(query: String, page: Int): Observable<CharacterWrapper> =
        service.searchPeople(query, page)
}