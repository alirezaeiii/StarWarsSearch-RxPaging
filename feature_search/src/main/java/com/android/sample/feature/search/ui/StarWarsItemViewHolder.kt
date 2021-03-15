package com.android.sample.feature.search.ui

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.commons.extension.layoutInflater
import com.android.sample.core.model.Person
import com.android.sample.feature.search.R
import com.android.sample.feature.search.databinding.StarWarsItemBinding

class StarWarsItemViewHolder(val binding: StarWarsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): StarWarsItemViewHolder {
            val binding: StarWarsItemBinding =
                StarWarsItemBinding.inflate(parent.context.layoutInflater)
            return StarWarsItemViewHolder(binding)
        }
    }
}