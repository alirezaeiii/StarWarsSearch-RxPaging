package com.android.sample.feature.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.android.sample.commons.base.BaseFragment
import com.android.sample.feature.search.BR
import com.android.sample.feature.search.R
import com.android.sample.feature.search.databinding.FragmentSearchBinding
import com.android.sample.feature.search.di.DaggerSearchComponent
import com.android.sample.feature.search.di.SearchModule
import com.android.sample.feature.search.extension.setupActionBar
import com.android.sample.feature.search.viewmodel.SearchViewModel
import com.android.sample.starwars.StarWarsApplication.Companion.coreComponent

class SearchFragment : BaseFragment<SearchViewModel>() {

    /**
     * RecyclerView Adapter for converting a list of pokemon to items.
     */
    private lateinit var viewModelAdapter: MainAdapter

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            setVariable(BR.vm, viewModel)
            // Set the lifecycleOwner so DataBinding can observe LiveData
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.items.observe(viewLifecycleOwner, {
            viewModelAdapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, {
            viewModelAdapter.setNetworkState(it)
        })

        viewModelAdapter =
            MainAdapter({ viewModel.retry() }, MainAdapter.OnClickListener { person ->
                val destination =
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(person)
                with(findNavController()) {
                    currentDestination?.getAction(destination.actionId)
                        ?.let { navigate(destination) }
                }
            })

        with(binding) {
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = viewModelAdapter
            }

            (activity as AppCompatActivity).setupActionBar(toolbar) {
                setTitle(R.string.star_wars)
            }
        }

        viewModel.showQuery("a")

        return binding.root
    }
}