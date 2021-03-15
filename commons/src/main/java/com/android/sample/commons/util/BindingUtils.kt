package com.android.sample.commons.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("toVisibility")
fun View.toVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}