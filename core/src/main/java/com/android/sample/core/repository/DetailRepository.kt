package com.android.sample.core.repository

import com.android.sample.core.response.Film
import com.android.sample.core.response.Planet
import com.android.sample.core.response.Specie
import io.reactivex.Single

interface DetailRepository {

    fun getSpecie(url: String): Single<Specie>

    fun getPlanet(url: String): Single<Planet>

    fun getFilm(url: String): Single<Film>
}