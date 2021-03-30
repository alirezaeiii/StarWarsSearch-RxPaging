package com.android.sample.core.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

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