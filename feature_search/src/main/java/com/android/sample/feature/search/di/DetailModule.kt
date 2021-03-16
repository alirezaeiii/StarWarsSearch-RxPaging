package com.android.sample.feature.search.di

import com.android.sample.commons.extension.viewModel
import com.android.sample.commons.util.schedulers.BaseSchedulerProvider
import com.android.sample.commons.util.schedulers.SchedulerProvider
import com.android.sample.core.domain.GetFilmUseCase
import com.android.sample.core.domain.GetPlanetUseCase
import com.android.sample.core.domain.GetSpecieUseCase
import com.android.sample.feature.search.ui.DetailFragment
import com.android.sample.feature.search.viewmodel.DetailViewModel
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [SearchComponent].
 *
 * @see Module
 */
@Module
class DetailModule(private val fragment: DetailFragment) {

    /**
     * Create a provider method binding for [DetailViewModel].
     *
     * @return Instance of view model.
     * @see Provides
     */
    @Provides
    fun providesDetailViewModel(
            schedulerProvider: BaseSchedulerProvider,
            getSpecieUseCase: GetSpecieUseCase,
            getPlanetUseCase: GetPlanetUseCase,
            getFilmUseCase: GetFilmUseCase,
    ) = fragment.viewModel {
        DetailViewModel(schedulerProvider, fragment.args.character, getSpecieUseCase,
                getPlanetUseCase, getFilmUseCase)
    }

    @Provides
    internal fun provideSchedulerProvider(): BaseSchedulerProvider =
        SchedulerProvider()
}