package com.android.sample.common.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("toVisibility")
fun View.toVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("showLoading")
fun <T> View.showLoading(resource: Resource<T>?) {
    visibility = if (resource is Resource.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("showError")
fun <T> View.showError(resource: Resource<T>?) {
    visibility = if (resource is Resource.Error) View.VISIBLE else View.GONE
}

@BindingAdapter("showData")
fun <T> View.showData(resource: Resource<T>?) {
    visibility = if (resource is Resource.Success) View.VISIBLE else View.GONE
}