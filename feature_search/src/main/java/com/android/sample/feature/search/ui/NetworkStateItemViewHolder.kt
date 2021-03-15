package com.android.sample.feature.search.ui

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.sample.commons.extension.layoutInflater
import com.android.sample.commons.paging.NetworkState
import com.android.sample.commons.paging.Status.FAILED
import com.android.sample.commons.paging.Status.RUNNING
import com.android.sample.commons.util.toVisibility
import com.android.sample.feature.search.R

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(
    root: View,
    retryCallback: () -> Unit
) : RecyclerView.ViewHolder(root) {

    private val networkStateLayout = root.findViewById<LinearLayout>(R.id.network_state_layout)
    private val progressBar = root.findViewById<ImageView>(R.id.progress_bar)
    private val retry = root.findViewById<Button>(R.id.retry_button)
    private val errorMsg = root.findViewById<TextView>(R.id.error_msg)

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    fun bindTo(networkState: NetworkState?, position: Int) {
        progressBar.toVisibility(networkState?.status == RUNNING)
        retry.toVisibility(networkState?.status == FAILED)
        errorMsg.toVisibility(networkState?.msg != null)
        errorMsg.text = networkState?.msg
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val view = parent.context.layoutInflater
                .inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view, retryCallback)
        }
    }
}