package com.android.sample.feature.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.common.util.Resource
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.common.util.schedulers.ImmediateSchedulerProvider
import com.android.sample.core.domain.GetFilmUseCase
import com.android.sample.core.domain.GetPlanetUseCase
import com.android.sample.core.domain.GetSpecieUseCase
import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.response.Character
import com.android.sample.core.response.Film
import com.android.sample.core.response.Planet
import com.android.sample.core.response.Specie
import com.android.sample.feature.search.viewmodel.DetailViewModel
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks
import java.util.concurrent.TimeUnit

class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DetailRepository

    private lateinit var schedulerProvider: ImmediateSchedulerProvider

    private lateinit var character: Character
    private lateinit var specie: Specie
    private lateinit var planet: Planet
    private lateinit var film: Film

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        initMocks(this)

        // Make the sure that all schedulers are immediate.
        schedulerProvider = ImmediateSchedulerProvider()

        character = Character("Ali", "127", "1385",
                listOf("url1", "url2"), listOf("url1", "url2"))

        specie = Specie("Ali", "Persian", "Iran")
        planet = Planet("")
        film = Film("")
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        `when`(repository.getSpecie(anyString())).thenReturn(Single.just(specie))
        `when`(repository.getPlanet(anyString())).thenReturn(Single.just(planet))
        `when`(repository.getFilm(anyString())).thenReturn(Single.just(film))

        viewModel = DetailViewModel(schedulerProvider, character, GetSpecieUseCase(repository),
                GetPlanetUseCase(repository), GetFilmUseCase(repository))

        viewModel.liveData.value.let {
            assertThat(it, `is`(Resource.Loading))
        }

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is Resource.Success) {
                it.data?.let { data ->
                    assertTrue(data.films.isNotEmpty())
                    assertTrue(data.species.isNotEmpty())
                }
            } else {
                fail("Wrong type " + it)
            }
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_specie_shouldReturnError() {
        `when`(repository.getSpecie(anyString())).thenReturn(Single.error(Exception("error")))
        `when`(repository.getPlanet(anyString())).thenReturn(Single.just(planet))
        `when`(repository.getFilm(anyString())).thenReturn(Single.just(film))

        viewModel = DetailViewModel(schedulerProvider, character, GetSpecieUseCase(repository),
                GetPlanetUseCase(repository), GetFilmUseCase(repository))

        //schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is Resource.Error) {
                assertThat(it.message, `is`(notNullValue()))
                assertThat(it.message, `is`("error"))
            } else {
                fail("Wrong type " + it)
            }
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_planet_shouldReturnError() {
        `when`(repository.getSpecie(anyString())).thenReturn(Single.just(specie))
        `when`(repository.getPlanet(anyString())).thenReturn(Single.error(Exception("error")))
        `when`(repository.getFilm(anyString())).thenReturn(Single.just(film))

        viewModel = DetailViewModel(schedulerProvider, character, GetSpecieUseCase(repository),
                GetPlanetUseCase(repository), GetFilmUseCase(repository))

        //schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is Resource.Error) {
                assertThat(it.message, `is`(notNullValue()))
                assertThat(it.message, `is`("error"))
            } else {
                fail("Wrong type " + it)
            }
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_film_shouldReturnError() {
        `when`(repository.getSpecie(anyString())).thenReturn(Single.just(specie))
        `when`(repository.getPlanet(anyString())).thenReturn(Single.just(planet))
        `when`(repository.getFilm(anyString())).thenReturn(Single.error(Exception("error")))

        viewModel = DetailViewModel(schedulerProvider, character, GetSpecieUseCase(repository),
                GetPlanetUseCase(repository), GetFilmUseCase(repository))

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is Resource.Error) {
                assertThat(it.message, `is`(notNullValue()))
                assertThat(it.message, `is`("error"))
            } else {
                fail("Wrong type " + it)
            }
        }
    }
}