package com.android.sample.feature.search.di

import androidx.navigation.fragment.navArgs
import com.android.sample.common.extension.viewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.GetFilmUseCase
import com.android.sample.core.domain.GetPlanetUseCase
import com.android.sample.core.domain.GetSpecieUseCase
import com.android.sample.core.response.Character
import com.android.sample.feature.search.ui.detail.DetailFragment
import com.android.sample.feature.search.ui.detail.DetailFragmentArgs
import com.android.sample.feature.search.viewmodel.DetailViewModel
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
    internal fun provideCharacter(): Character {
        val args: DetailFragmentArgs by fragment.navArgs()
        return args.character
    }
}