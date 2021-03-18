package com.android.sample.core.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.core.domain.GetPlanetUseCase
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.response.Planet
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPlanetUseCaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: StarWarsService

    @Test
    fun getPlanet() {
        val detailRepository = DetailRepository(service)
        val getPlanetUseCase = GetPlanetUseCase(detailRepository)

        val planet = Planet("")

        `when`(service.getPlanet(anyString())).thenReturn(Single.just(planet))

        val testObserver = getPlanetUseCase(anyString()).test()

        testObserver.assertValues(planet)

        testObserver.dispose()
    }
}