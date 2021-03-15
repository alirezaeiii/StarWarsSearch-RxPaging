package com.android.sample.feature.search.di

import com.android.sample.commons.extension.viewModel
import com.android.sample.commons.util.schedulers.BaseSchedulerProvider
import com.android.sample.commons.util.schedulers.SchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.feature.search.ui.SearchFragment
import com.android.sample.feature.search.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [SearchComponent].
 *
 * @see Module
 */
@Module
class SearchModule(
    private val fragment: SearchFragment
) {

    /**
     * Create a provider method binding for [SearchViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @Provides
    fun providesSearchViewModel(useCase: SearchPeopleUseCase,
                                schedulerProvider: BaseSchedulerProvider) = fragment.viewModel {
        SearchViewModel(useCase, schedulerProvider)
    }

    @Provides
    internal fun provideSchedulerProvider(): BaseSchedulerProvider =
        SchedulerProvider()
}