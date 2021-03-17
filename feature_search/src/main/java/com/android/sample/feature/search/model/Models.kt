package com.android.sample.feature.search.model

import com.android.sample.core.response.Film

class DetailWrapper(
    val species: List<SpecieWrapper>,
    val films: List<Film>,
)

class SpecieWrapper(
    val name: String?,
    val language: String?,
    val population: String,
)