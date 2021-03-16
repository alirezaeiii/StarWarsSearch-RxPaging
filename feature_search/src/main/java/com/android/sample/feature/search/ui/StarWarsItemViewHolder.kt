package com.android.sample.feature.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.commons.extension.layoutInflater
import com.android.sample.core.response.Character
import com.android.sample.feature.search.databinding.StarWarsItemBinding

class StarWarsItemViewHolder(private val binding: StarWarsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(character: Character?, personCallback: MainAdapter.OnClickListener) {
        with(binding) {
            person = character
            callback = personCallback
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): StarWarsItemViewHolder {
            val binding = StarWarsItemBinding.inflate(
                parent.context.layoutInflater,
                parent,
                false
            )
            return StarWarsItemViewHolder(binding)
        }
    }
}