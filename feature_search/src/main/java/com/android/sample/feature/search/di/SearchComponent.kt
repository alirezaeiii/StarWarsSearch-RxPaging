package com.android.sample.feature.search.di

import com.android.sample.core.di.CoreComponent
import com.android.sample.core.di.FeatureScope
import com.android.sample.core.di.SchedulerModule
import com.android.sample.feature.search.ui.search.SearchFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [SearchModule].
 *
 * @see Component
 */
@FeatureScope
@Component(modules = [SearchModule::class, SchedulerModule::class],
        dependencies = [CoreComponent::class]
)
interface SearchComponent {

    /**
     * Inject dependencies on component.
     *
     * @param searchFragment
     */
    fun inject(searchFragment: SearchFragment)
}