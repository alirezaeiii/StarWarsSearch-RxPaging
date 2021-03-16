package com.android.sample.feature.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.sample.commons.base.BaseFragment
import com.android.sample.feature.search.databinding.FragmentDetailBinding
import com.android.sample.feature.search.di.DaggerDetailComponent
import com.android.sample.feature.search.di.DetailModule
import com.android.sample.feature.search.viewmodel.DetailViewModel
import com.android.sample.starwars.StarWarsApplication

class DetailFragment : BaseFragment<DetailViewModel>() {

    internal val args: DetailFragmentArgs by navArgs()

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
            person = args.person
        }

        return binding.root
    }
}