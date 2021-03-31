package com.android.sample.core.repository

import com.android.sample.core.network.StarWarsService
import com.android.sample.core.response.CharacterWrapper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
        private val service: StarWarsService,
) : SearchRepository {

    override fun searchPeople(query: String, page: Int): Observable<CharacterWrapper> =
            service.searchPeople(query, page)
}