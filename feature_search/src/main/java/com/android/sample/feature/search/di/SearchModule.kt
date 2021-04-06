package com.android.sample.feature.search.di

import android.app.Application
import com.android.sample.common.extension.viewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.SearchRepository
import com.android.sample.core.repository.SearchRepositoryImpl
import com.android.sample.feature.search.ui.search.SearchFragment
import com.android.sample.feature.search.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [SearchComponent].
 *
 * @see Module
 */
@Module
class SearchModule(private val fragment: SearchFragment) {

    /**
     * Create a provider method binding for [SearchViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @Provides
    fun providesSearchViewModel(
            useCase: SearchPeopleUseCase,
            schedulerProvider: BaseSchedulerProvider,
            application: Application,
    ) = fragment.viewModel {
        SearchViewModel(useCase, schedulerProvider, application)
    }

    @Provides
    internal fun provideSearchRepository(service: StarWarsService): SearchRepository =
            SearchRepositoryImpl(service)
}