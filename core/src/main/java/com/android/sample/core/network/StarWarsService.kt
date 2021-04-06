package com.android.sample.core.network

import com.android.sample.core.response.Film
import com.android.sample.core.response.CharacterWrapper
import com.android.sample.core.response.Planet
import com.android.sample.core.response.Specie
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * A retrofit service to fetch items.
 */
interface StarWarsService {

    @GET("people/")
    fun searchPeople(
        @Query("search") query: String,
        @Query("page") page: Int,
    ): Observable<CharacterWrapper>

    @GET
    fun getSpecie(@Url url: String) : Single<Specie>

    @GET
    fun getPlanet(@Url url: String) : Single<Planet>

    @GET
    fun getFilm(@Url url: String) : Single<Film>
}

