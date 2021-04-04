package com.android.sample.feature.search.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.android.sample.common.base.BaseFragment
import com.android.sample.feature.search.BR
import com.android.sample.feature.search.databinding.FragmentSearchBinding
import com.android.sample.feature.search.di.DaggerSearchComponent
import com.android.sample.feature.search.di.SearchModule
import com.android.sample.feature.search.viewmodel.SearchViewModel
import com.android.sample.starwars.StarWarsApplication.Companion.coreComponent

class SearchFragment : BaseFragment<SearchViewModel>() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

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

        _binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            setVariable(BR.vm, viewModel)
            // Set the lifecycleOwner so DataBinding can observe LiveData
            lifecycleOwner = viewLifecycleOwner
        }

        val viewModelAdapter =
                MainAdapter({ viewModel.retry() }, MainAdapter.OnClickListener { person ->
                    val destination =
                            SearchFragmentDirections.actionSearchFragmentToDetailFragment(person)
                    with(findNavController()) {
                        currentDestination?.getAction(destination.actionId)
                                ?.let { navigate(destination) }
                    }
                })

        viewModel.liveData.observe(viewLifecycleOwner, {
            viewModelAdapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, {
            viewModelAdapter.setNetworkState(it)
        })

        with(binding) {

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
                    if (savedInstanceState == null ||
                            savedInstanceState.getBoolean(EMPTY_VIEW_KEY)) {
                        emptyLayout.visibility = View.INVISIBLE
                    }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EMPTY_VIEW_KEY, binding.recyclerView.adapter?.itemCount == 0)
    }
}

private const val EMPTY_VIEW_KEY = "empty_view"