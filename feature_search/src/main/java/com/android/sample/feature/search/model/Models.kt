package com.android.sample.feature.search.model

import android.content.Context
import com.android.sample.core.response.Film
import com.android.sample.feature.search.R

class DetailWrapper(
    val species: List<SpecieWrapper>,
    val films: List<Film>,
)

class SpecieWrapper(
    val name: String,
    val language: String,
    val population: String,
) {
    companion object {
        fun getUnAvailableSpecie(context: Context): SpecieWrapper {
            val notAvailableLabel = context.getString(R.string.label_not_available)
            return SpecieWrapper(notAvailableLabel, notAvailableLabel, notAvailableLabel)
        }
    }
}