package com.android.sample.feature.search.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.sample.feature.search.R

@BindingAdapter("height")
fun TextView.setHeightText(height: String) {
    val value = height.toDoubleOrNull()
    text = if (value == null) {
        height
    } else {
        context.getString(
            R.string.person_height,
            height,
            String.format("%.3f", (FEET_CONVERTER * value))
        )
    }
}

private const val FEET_CONVERTER = 0.0328