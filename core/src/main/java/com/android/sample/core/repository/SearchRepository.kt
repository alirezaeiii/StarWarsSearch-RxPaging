package com.android.sample.core.repository

import com.android.sample.core.model.PeopleWrapper
import com.android.sample.core.network.StarWarsService
import io.reactivex.Observable
import javax.inject.Inject

class SearchRepository @Inject constructor(private val service: StarWarsService) {

    fun searchPeople(query: String, page: Int): Observable<PeopleWrapper> =
        service.searchPeople(query, page)
}