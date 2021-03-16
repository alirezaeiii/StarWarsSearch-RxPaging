package com.android.sample.feature.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.commons.extension.layoutInflater
import com.android.sample.core.response.Person
import com.android.sample.feature.search.databinding.StarWarsItemBinding

class StarWarsItemViewHolder(private val binding: StarWarsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(person: Person?, personCallback: MainAdapter.OnClickListener) {
        with(binding) {
            personItem = person
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