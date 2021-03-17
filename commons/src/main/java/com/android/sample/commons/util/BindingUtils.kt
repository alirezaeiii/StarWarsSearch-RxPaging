package com.android.sample.commons.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("toVisibility")
fun View.toVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("showLoading")
fun View.showLoading(resource: Resource<*>?) {
    visibility = if (resource is Resource.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("showError")
fun View.showError(resource: Resource<*>?) {
    visibility = if (resource is Resource.Error) View.VISIBLE else View.GONE
}

@BindingAdapter("showData")
fun View.showData(resource: Resource<*>?) {
    visibility = if (resource is Resource.Success) View.VISIBLE else View.GONE
}