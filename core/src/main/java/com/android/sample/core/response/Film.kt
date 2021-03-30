package com.android.sample.core.response

import com.squareup.moshi.Json

data class Film(
    @Json(name = "opening_crawl")
    val openingCrawl: String
)