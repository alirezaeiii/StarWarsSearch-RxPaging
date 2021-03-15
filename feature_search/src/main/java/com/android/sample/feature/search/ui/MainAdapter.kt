package com.android.sample.feature.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.commons.extension.layoutInflater
import com.android.sample.core.model.Person
import com.android.sample.feature.search.databinding.StarWarsItemBinding

class MainAdapter(
    //private val callback: OnClickListener
) : ListAdapter<Person, MainAdapter.MainViewHolder>(DiffCallback) {

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder.from(parent)

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder for Post items. All work is done by data binding.
     */
    class MainViewHolder(private val binding: StarWarsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            with(binding) {
                personItem = person
                //callback = pokemonCallback
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder {
                val binding = StarWarsItemBinding.inflate(
                    parent.context.layoutInflater,
                    parent,
                    false
                )
                return MainViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Person]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(
            oldItem: Person, newItem: Person
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Person, newItem: Person
        ): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Person]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Person]
     */
    class OnClickListener(val clickListener: (pokemon: Person) -> Unit) {
        fun onClick(pokemon: Person) =
            clickListener(pokemon)
    }
}