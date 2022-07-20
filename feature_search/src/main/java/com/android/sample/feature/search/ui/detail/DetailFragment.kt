package com.android.sample.feature.search.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.sample.common.base.BaseFragment
import com.android.sample.common.util.Resource
import com.android.sample.core.response.Character
import com.android.sample.feature.search.BR
import com.android.sample.feature.search.R
import com.android.sample.feature.search.databinding.FragmentDetailBinding
import com.android.sample.feature.search.di.DaggerDetailComponent
import com.android.sample.feature.search.di.DetailModule
import com.android.sample.feature.search.model.SpecieWrapper.Companion.getUnAvailableSpecie
import com.android.sample.feature.search.viewmodel.DetailViewModel
import com.android.sample.starwars.StarWarsApplication
import javax.inject.Inject

class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>(
    R.layout.fragment_detail, BR.vm
) {

    @Inject
    lateinit var character: Character

    @Inject
    lateinit var filmAdapter: FilmAdapter

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        binding.character = this@DetailFragment.character

        with(binding) {

            toolbarCharacterDetails.apply {
                setNavigationOnClickListener { findNavController().navigateUp() }
            }

            recyclerViewFilm.apply {
                setHasFixedSize(true)
                adapter = filmAdapter
            }

            viewModel.liveData.observe(viewLifecycleOwner) { resource ->
                if (resource is Resource.Success) {
                    val species = resource.data?.species
                    specie = if (species.isNullOrEmpty()) {
                        getUnAvailableSpecie(requireContext())
                    } else {
                        species[0]
                    }
                    filmAdapter.submitList(resource.data?.films)
                }
            }
        }

        return binding.root
    }
}