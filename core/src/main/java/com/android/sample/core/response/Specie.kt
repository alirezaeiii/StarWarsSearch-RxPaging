package com.android.sample.core.response

import com.squareup.moshi.Json

class Specie(
    val name: String,
    val language: String,
    @Json(name = "homeworld")
    val homeWorld: String,
)