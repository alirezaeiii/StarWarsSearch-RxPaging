package com.android.sample.core.response

import com.squareup.moshi.Json

class CharacterWrapper(
        @Json(name = "results")
        val wrapper: List<Character>,
        val next: String?
)