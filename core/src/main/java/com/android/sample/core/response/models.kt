package com.android.sample.core.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

class PeopleWrapper(
        @Json(name = "results")
        val wrapper: List<Person>,
        val next: String?,
)

@Parcelize
data class Person(
        val name: String,
        val height: String,
        @Json(name = "birth_year")
        val birthYear: String,
        val species: List<String>,
        val films: List<String>,
) : Parcelable

class Specie(
        val name: String,
        val language: String,
        @Json(name = "homeworld")
        val homeWorld: String,
)

class Planet(
        val population: String,
)

class Film(
        @Json(name = "opening_crawl")
        val openingCrawl: String
)