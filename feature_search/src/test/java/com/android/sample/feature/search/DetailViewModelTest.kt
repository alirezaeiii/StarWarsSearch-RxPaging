package com.android.sample.feature.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.common.util.Resource
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks

class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DetailRepository

    private lateinit var specie: Specie
    private lateinit var planet: Planet
    private lateinit var film: Film

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        initMocks(this)

        // Make the sure that all schedulers are immediate.
        val schedulerProvider = ImmediateSchedulerProvider()

        val character = Character(
            "Ali", "127", "1385", emptyList(), emptyList()
        )

        viewModel = DetailViewModel(
            schedulerProvider, character, GetSpecieUseCase(repository),
            GetPlanetUseCase(repository), GetFilmUseCase(repository)
        )

        specie = Specie("Ali", "Persian", "Iran")
        planet = Planet("")
        film = Film("")
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        `when`(repository.getSpecie(anyString())).thenReturn(Single.just(specie))
        `when`(repository.getPlanet(anyString())).thenReturn(Single.just(planet))
        `when`(repository.getFilm(anyString())).thenReturn(Single.just(film))

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is Resource.Success) {
                it.data?.let { data ->
                    assertTrue(data.films.isEmpty())
                    assertTrue(data.species.isEmpty())
                }
            }
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_specie_shouldReturnError() {
        `when`(repository.getSpecie(anyString())).thenReturn(Single.error(Exception("error")))
        `when`(repository.getPlanet(anyString())).thenReturn(Single.just(planet))
        `when`(repository.getFilm(anyString())).thenReturn(Single.just(film))

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is Resource.Error) {
                assertThat(it.message, `is`(notNullValue()))
                assertThat(it.message, `is`("error"))
            }
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_planet_shouldReturnError() {
        `when`(repository.getSpecie(anyString())).thenReturn(Single.just(specie))
        `when`(repository.getPlanet(anyString())).thenReturn(Single.error(Exception("error")))
        `when`(repository.getFilm(anyString())).thenReturn(Single.just(film))

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is Resource.Error) {
                assertThat(it.message, `is`(notNullValue()))
                assertThat(it.message, `is`("error"))
            }
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_film_shouldReturnError() {
        `when`(repository.getSpecie(anyString())).thenReturn(Single.just(specie))
        `when`(repository.getPlanet(anyString())).thenReturn(Single.just(planet))
        `when`(repository.getFilm(anyString())).thenReturn(Single.error(Exception("error")))

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is Resource.Error) {
                assertThat(it.message, `is`(notNullValue()))
                assertThat(it.message, `is`("error"))
            }
        }
    }
}