package com.android.sample.core.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.response.Film
import com.android.sample.core.response.Planet
import com.android.sample.core.response.Specie
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DetailRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: StarWarsService

    private lateinit var detailRepository: DetailRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        detailRepository = DetailRepositoryImpl(service)
    }

    @Test
    fun getSpecie() {
        val specie = Specie("Ali", "Persian", "Iran")

        `when`(service.getSpecie(anyString())).thenReturn(Single.just(specie))

        val testObserver = detailRepository.getSpecie(anyString()).test()

        testObserver.assertValues(specie)

        testObserver.dispose()
    }

    @Test
    fun getPlanet() {
        val planet = Planet("")

        `when`(service.getPlanet(anyString())).thenReturn(Single.just(planet))

        val testObserver = detailRepository.getPlanet(anyString()).test()

        testObserver.assertValues(planet)

        testObserver.dispose()
    }

    @Test
    fun getFilm() {
        val film = Film("")

        `when`(service.getFilm(anyString())).thenReturn(Single.just(film))

        val testObserver = detailRepository.getFilm(anyString()).test()

        testObserver.assertValues(film)

        testObserver.dispose()
    }
}