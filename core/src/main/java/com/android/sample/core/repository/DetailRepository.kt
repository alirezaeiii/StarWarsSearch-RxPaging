package com.android.sample.core.repository

import com.android.sample.core.response.Film
import com.android.sample.core.response.Planet
import com.android.sample.core.response.Specie
import com.android.sample.core.network.StarWarsService
import io.reactivex.Single
import javax.inject.Inject

class DetailRepository @Inject constructor(private val service: StarWarsService) {

    fun getSpecie(url: String): Single<Specie> = service.getSpecie(url)

    fun getPlanet(url: String): Single<Planet> = service.getPlanet(url)

    fun getFilm(url: String): Single<Film> = service.getFilm(url)
}