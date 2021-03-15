package com.android.sample.core.model

import com.squareup.moshi.Json

class PeopleWrapper(
    @Json(name = "results")
    val wrapper: List<Person>
)

data class Person(
    val name: String,
    val height: String,
    @Json(name = "birth_year")
    val birthYear: String
)