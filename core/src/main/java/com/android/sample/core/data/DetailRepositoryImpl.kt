package com.android.sample.core.data

import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.response.Film
import com.android.sample.core.response.Planet
import com.android.sample.core.response.Specie
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepositoryImpl @Inject constructor(
        private val service: StarWarsService,
) : DetailRepository {

    override fun getSpecie(url: String): Single<Specie> = service.getSpecie(url)

    override fun getPlanet(url: String): Single<Planet> = service.getPlanet(url)

    override fun getFilm(url: String): Single<Film> = service.getFilm(url)
}