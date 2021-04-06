package com.android.sample.feature.search.di

import com.android.sample.core.di.CoreComponent
import com.android.sample.core.di.FeatureScope
import com.android.sample.feature.search.ui.detail.DetailFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [DetailModule].
 *
 * @see Component
 */
@FeatureScope
@Component(modules = [DetailModule::class],
    dependencies = [CoreComponent::class]
)
interface DetailComponent {

    /**
     * Inject dependencies on component.
     *
     * @param detailFragment
     */
    fun inject(detailFragment: DetailFragment)
}