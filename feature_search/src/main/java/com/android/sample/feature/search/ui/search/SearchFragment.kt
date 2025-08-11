package com.android.sample.feature.search.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.android.sample.common.base.BaseFragment
import com.android.sample.feature.search.BR
import com.android.sample.feature.search.R
import com.android.sample.feature.search.databinding.FragmentSearchBinding
import com.android.sample.feature.search.di.DaggerSearchComponent
import com.android.sample.feature.search.di.SearchModule
import com.android.sample.feature.search.ui.search.MainAdapter.OnClickListener
import com.android.sample.feature.search.viewmodel.SearchViewModel
import com.android.sample.starwars.StarWarsApplication.Companion.coreComponent

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(
    R.layout.fragment_search, BR.vm
) {

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerSearchComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .searchModule(SearchModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val viewModelAdapter =
            MainAdapter({ viewModel.retry() }, OnClickListener { character ->
                val destination =
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(character)
                with(findNavController()) {
                    currentDestination?.getAction(destination.actionId)
                        ?.let { navigate(destination) }
                }
            })

        viewModel.pagedList.observe(viewLifecycleOwner) {
            viewModelAdapter.submitList(it)
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            viewModelAdapter.setNetworkState(it)
        }

        val searchCloseIconButtonId =
            resources.getIdentifier("android:id/search_close_btn", null, null)

        with(binding) {

            val searchClose: ImageView = searchView.findViewById(searchCloseIconButtonId)
            val searchCloseIconColor = ResourcesCompat.getColor(resources, R.color.gray, null)
            searchClose.setColorFilter(searchCloseIconColor)

            recyclerView.apply {
                setHasFixedSize(true)
                adapter = viewModelAdapter
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    search(query)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    if (query.isNotEmpty()) {
                        search(query)
                    }
                    emptyLayout.visibility = View.INVISIBLE
                    return true
                }
            })
        }
        return binding.root
    }

    private fun search(query: String) {
        if (viewModel.showQuery(query)) {
            binding.recyclerView.scrollToPosition(0)
            (binding.recyclerView.adapter as MainAdapter).submitList(null)
        }
    }
}