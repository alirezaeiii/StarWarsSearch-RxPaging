package com.android.sample.core.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

class PeopleWrapper(
        @Json(name = "results")
        val wrapper: List<Character>,
        val next: String?
)

@Parcelize
data class Character(
        val name: String,
        val height: String,
        @Json(name = "birth_year")
        val birthYear: String,
        @Json(name = "species")
        val specieUrls: List<String>,
        @Json(name = "films")
        val filmUrls: List<String>,
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

data class Film(
        @Json(name = "opening_crawl")
        val openingCrawl: String
)