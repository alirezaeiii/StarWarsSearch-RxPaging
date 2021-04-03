package com.android.sample.feature.search.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.sample.common.base.BaseFragment
import com.android.sample.common.util.Resource
import com.android.sample.feature.search.BR
import com.android.sample.feature.search.databinding.FragmentDetailBinding
import com.android.sample.feature.search.di.DaggerDetailComponent
import com.android.sample.feature.search.di.DetailModule
import com.android.sample.feature.search.viewmodel.DetailViewModel
import com.android.sample.starwars.StarWarsApplication

class DetailFragment : BaseFragment<DetailViewModel>() {

    private val args: DetailFragmentArgs by navArgs()

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerDetailComponent
            .builder()
            .coreComponent(StarWarsApplication.coreComponent(requireContext()))
            .detailModule(DetailModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            setVariable(BR.vm, viewModel)
            character = args.character
            // Set the lifecycleOwner so DataBinding can observe LiveData
            lifecycleOwner = viewLifecycleOwner
        }

        val filmAdapter = FilmAdapter()

        with(binding) {

            toolbarCharacterDetails.apply {
                setNavigationOnClickListener { findNavController().navigateUp() }
            }

            recyclerViewFilm.apply {
                setHasFixedSize(true)
                adapter = filmAdapter
            }

            viewModel.liveData.observe(viewLifecycleOwner, { resource ->
                if (resource is Resource.Success) {
                    val species = resource.data?.species
                    if (species.isNullOrEmpty()) {
                        layoutSpecie.visibility = View.GONE
                    } else {
                        specie = species[0]
                    }
                    filmAdapter.submitList(resource.data?.films)
                }
            })
        }

        return binding.root
    }
}