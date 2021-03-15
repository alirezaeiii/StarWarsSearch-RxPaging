package com.android.sample.commons.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: VM

    /**
     * Called to initialize dagger injection dependency graph when fragment is attached.
     */
    abstract fun onInitDependencyInjection()

    /**
     * Called when a fragment is first attached to its context.
     *
     * @param context The application context.
     *
     * @see Fragment.onAttach
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onInitDependencyInjection()
    }
}