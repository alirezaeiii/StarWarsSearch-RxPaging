package com.android.sample.core.repository

import com.android.sample.core.model.Person
import com.android.sample.core.network.StarWarsService
import io.reactivex.Single
import javax.inject.Inject

class SearchRepository @Inject constructor(private val service: StarWarsService) {

    fun searchPeople(query: String): Single<List<Person>> =
        service.searchPeople(query).map { it.wrapper }
}