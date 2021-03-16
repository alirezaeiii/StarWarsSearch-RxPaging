package com.android.sample.feature.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.sample.feature.search.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        
        val args: DetailFragmentArgs by navArgs()

        val binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            person = args.person
        }

        return binding.root
    }
}