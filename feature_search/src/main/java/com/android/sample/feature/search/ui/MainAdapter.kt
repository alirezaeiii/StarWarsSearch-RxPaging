package com.android.sample.feature.search.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.commons.paging.NetworkState
import com.android.sample.core.response.Person
import com.android.sample.feature.search.R

class MainAdapter(
    private val retryCallback: () -> Unit,
    private val callback: OnClickListener
) : PagedListAdapter<Person, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}) {

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.star_wars_item ->
                (holder as StarWarsItemViewHolder).bindTo(getItem(position), callback)

            R.layout.network_state_item ->
                (holder as NetworkStateItemViewHolder).bindTo(networkState, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.star_wars_item -> StarWarsItemViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.star_wars_item
        }
    }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Person]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Person]
     */
    class OnClickListener(val clickListener: (person: Person) -> Unit) {
        fun onClick(person: Person) =
            clickListener(person)
    }
}