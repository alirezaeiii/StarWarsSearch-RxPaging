package com.android.sample.feature.search.di

import com.android.sample.common.extension.viewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.common.util.schedulers.SchedulerProvider
import com.android.sample.core.domain.GetFilmUseCase
import com.android.sample.core.domain.GetPlanetUseCase
import com.android.sample.core.domain.GetSpecieUseCase
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.repository.DetailRepositoryImpl
import com.android.sample.core.response.Character
import com.android.sample.feature.search.ui.detail.DetailFragment
import com.android.sample.feature.search.ui.detail.DetailFragmentArgs
import com.android.sample.feature.search.viewmodel.DetailViewModel
import com.android.sample.starwars.R
import dagger.Module
import dagger.Provides

/**
 * Class that contributes to the object graph [DetailComponent].
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
            character: Character,
    ) = fragment.viewModel {
        DetailViewModel(schedulerProvider, character, getSpecieUseCase,
                getPlanetUseCase, getFilmUseCase)
    }

    @Provides
    internal fun provideSchedulerProvider(): BaseSchedulerProvider =
            SchedulerProvider()

    @Provides
    internal fun provideDetailRepository(service: StarWarsService): DetailRepository =
            DetailRepositoryImpl(service)

    @Provides
    internal fun provideCharacter(): Character {
        val navHostFragment = fragment.requireActivity().supportFragmentManager
                .findFragmentById(R.id.navHostFragment)
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        return DetailFragmentArgs.fromBundle(fragment.requireArguments()).character
    }
}