package com.android.sample.feature.search.extension

val String.feet : Double get() = FEET_CONVERTER * this.toDouble()

private const val FEET_CONVERTER = 0.0328