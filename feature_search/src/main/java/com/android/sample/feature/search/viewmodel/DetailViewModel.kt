package com.android.sample.feature.search.viewmodel

import com.android.sample.common.base.BaseViewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.GetFilmUseCase
import com.android.sample.core.domain.GetPlanetUseCase
import com.android.sample.core.domain.GetSpecieUseCase
import com.android.sample.core.response.Character
import com.android.sample.core.response.Film
import com.android.sample.feature.search.model.DetailWrapper
import com.android.sample.feature.search.model.SpecieWrapper
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    character: Character,
    getSpecieUseCase: GetSpecieUseCase,
    getPlanetUseCase: GetPlanetUseCase,
    getFilmUseCase: GetFilmUseCase,
) : BaseViewModel<DetailWrapper>(schedulerProvider,
        Single.zip(getSpeciesWrapper(character, getSpecieUseCase, getPlanetUseCase),
                getFilms(character, getFilmUseCase), { species, films ->
            DetailWrapper(species, films)
        }))

private fun getSpeciesWrapper(
    character: Character, getSpecieUseCase: GetSpecieUseCase, getPlanetUseCase: GetPlanetUseCase,
): Single<List<SpecieWrapper>> {
    var name: String? = null
    var language: String? = null
    return Flowable.fromIterable(character.specieUrls)
            .flatMapSingle { specieUrl -> getSpecieUseCase(specieUrl) }
            .flatMapSingle { specie ->
                name = specie.name
                language = specie.language
                getPlanetUseCase(specie.homeWorld)
            }.map { planet ->
                SpecieWrapper(name, language, planet.population)
            }.toList()
}

private fun getFilms(character: Character, getFilmUseCase: GetFilmUseCase): Single<List<Film>> {
    return Flowable.fromIterable(character.filmUrls)
            .flatMapSingle { filmUrl -> getFilmUseCase(filmUrl) }
            .toList()
}