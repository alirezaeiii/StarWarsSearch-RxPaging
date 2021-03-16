package com.android.sample.feature.search.viewmodel

import com.android.sample.commons.base.BaseViewModel
import com.android.sample.commons.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.GetFilmUseCase
import com.android.sample.core.domain.GetPlanetUseCase
import com.android.sample.core.domain.GetSpecieUseCase
import com.android.sample.core.response.Film
import com.android.sample.core.response.Person
import io.reactivex.Flowable
import io.reactivex.Single
import com.android.sample.feature.search.viewmodel.DetailViewModel.DetailWrapper
import javax.inject.Inject

class DetailViewModel @Inject constructor(
        schedulerProvider: BaseSchedulerProvider,
        person: Person,
        getSpecieUseCase: GetSpecieUseCase,
        getPlanetUseCase: GetPlanetUseCase,
        getFilmUseCase: GetFilmUseCase,
) : BaseViewModel<DetailWrapper>(schedulerProvider,
        Single.zip(getSpeciesWrapper(person, getSpecieUseCase, getPlanetUseCase),
                getFilms(person, getFilmUseCase), { species, films ->
            DetailWrapper(species, films)
        })) {

    class DetailWrapper(
            val species: List<SpecieWrapper>,
            val films: List<Film>,
    )
}

private fun getSpeciesWrapper(
        person: Person, getSpecieUseCase: GetSpecieUseCase, getPlanetUseCase: GetPlanetUseCase,
): Single<List<SpecieWrapper>> {
    var name: String? = null
    var language: String? = null
    return Flowable.fromIterable(person.species)
            .flatMapSingle { specieUrl -> getSpecieUseCase(specieUrl) }
            .flatMapSingle { specie ->
                name = specie.name
                language = specie.language
                getPlanetUseCase(specie.homeWorld)
            }.map { planet ->
                SpecieWrapper(name, language, planet.population)
            }.toList()
}

private fun getFilms(person: Person, getFilmUseCase: GetFilmUseCase): Single<List<Film>> {
    return Flowable.fromIterable(person.films)
            .flatMapSingle { filmUrl -> getFilmUseCase(filmUrl) }
            .toList()
}

class SpecieWrapper(
        val name: String?,
        val language: String?,
        val population: String,
)