package com.android.sample.feature.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.commons.extension.layoutInflater
import com.android.sample.core.response.Film
import com.android.sample.feature.search.databinding.FilmItemBinding
import com.android.sample.feature.search.ui.FilmAdapter.FilmViewHolder

class FilmAdapter : ListAdapter<Film, FilmViewHolder>(DiffCallback) {

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmViewHolder.from(parent)

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder for Film items. All work is done by data binding.
     */
    class FilmViewHolder(private val binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filmItem: Film) {
            with(binding) {
                film = filmItem
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): FilmViewHolder {
                val binding = FilmItemBinding.inflate(
                    parent.context.layoutInflater,
                    parent,
                    false
                )
                return FilmViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Film]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(
            oldItem: Film, newItem: Film
        ): Boolean {
            return oldItem.openingCrawl == newItem.openingCrawl
        }

        override fun areContentsTheSame(
            oldItem: Film, newItem: Film
        ): Boolean {
            return oldItem == newItem
        }
    }
}