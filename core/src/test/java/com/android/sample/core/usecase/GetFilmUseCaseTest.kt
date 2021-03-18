package com.android.sample.core.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.commons.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.domain.GetFilmUseCase
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.response.Film
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
class GetFilmUseCaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: StarWarsService

    @Test
    fun getFilm() {
        val detailRepository = DetailRepository(service)
        val getFilmUseCase = GetFilmUseCase(detailRepository)

        val film = Film("")

        `when`(service.getFilm(anyString())).thenReturn(Single.just(film))

        val testObserver = getFilmUseCase(anyString()).test()

        testObserver.dispose()
    }
}