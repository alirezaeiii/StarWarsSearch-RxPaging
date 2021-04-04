package com.android.sample.feature.search.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.common.extension.layoutInflater
import com.android.sample.core.response.Character
import com.android.sample.feature.search.databinding.StarWarsItemBinding

class StarWarsViewHolder(private val binding: StarWarsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(character: Character?, personCallback: MainAdapter.OnClickListener) {
        with(binding) {
            person = character
            callback = personCallback
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): StarWarsViewHolder {
            val binding = StarWarsItemBinding.inflate(
                parent.context.layoutInflater,
                parent,
                false
            )
            return StarWarsViewHolder(binding)
        }
    }
}