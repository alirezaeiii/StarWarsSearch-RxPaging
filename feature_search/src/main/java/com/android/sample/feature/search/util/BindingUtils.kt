package com.android.sample.feature.search.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.sample.feature.search.R
import com.android.sample.feature.search.extension.feet

@BindingAdapter("height")
fun TextView.setHeightText(height: String) {
    text = this.context.getString(
        R.string.person_height,
        height,
        String.format("%.3f", height.feet)
    )
}