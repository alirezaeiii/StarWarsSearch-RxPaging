package com.android.sample.core.domain

import com.android.sample.core.response.Planet
import com.android.sample.core.repository.DetailRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPlanetUseCase @Inject constructor(
        private val detailRepository: DetailRepository,
) {
    operator fun invoke(url: String): Single<Planet> = detailRepository.getPlanet(url)
}