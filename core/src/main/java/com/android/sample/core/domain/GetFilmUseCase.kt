package com.android.sample.core.domain

import com.android.sample.core.response.Film
import com.android.sample.core.repository.DetailRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(
        private val detailRepository: DetailRepository,
) {
    operator fun invoke(url: String): Single<Film> = detailRepository.getFilm(url)
}