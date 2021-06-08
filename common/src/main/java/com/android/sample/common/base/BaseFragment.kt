package com.android.sample.common.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, T: ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModel: VM

    /**
     * Called to initialize dagger injection dependency graph when fragment is attached.
     */
    protected abstract fun onInitDependencyInjection()

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

    protected fun applyDataBinding(binding: T, variableId: Int) {
        binding.apply {
            setVariable(variableId, viewModel)
            // Set the lifecycleOwner so DataBinding can observe LiveData
            lifecycleOwner = viewLifecycleOwner
        }
    }
}