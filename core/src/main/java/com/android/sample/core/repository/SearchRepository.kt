package com.android.sample.core.repository

import com.android.sample.core.response.CharacterWrapper
import io.reactivex.Observable

interface SearchRepository {

    fun searchPeople(query: String, page: Int): Observable<CharacterWrapper>
}