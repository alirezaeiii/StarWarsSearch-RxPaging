package com.android.sample.core.domain

import com.android.sample.core.response.Specie
import com.android.sample.core.repository.DetailRepository
import io.reactivex.Single
import javax.inject.Inject

class GetSpecieUseCase @Inject constructor(
        private val detailRepository: DetailRepository,
) {
    operator fun invoke(url: String): Single<Specie> = detailRepository.getSpecie(url)
}