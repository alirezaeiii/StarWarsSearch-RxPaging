package com.android.sample.core.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.core.domain.GetSpecieUseCase
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.response.Specie
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
class GetSpecieUseCaseTet {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: StarWarsService

    @Test
    fun getSpecie() {
        val detailRepository = DetailRepository(service)
        val getSpecieUseCase = GetSpecieUseCase(detailRepository)

        val specie = Specie("Ali", "Persian", "Iran")

        `when`(service.getSpecie(anyString())).thenReturn(Single.just(specie))

        val testObserver = getSpecieUseCase(anyString()).test()

        testObserver.assertValues(specie)

        testObserver.dispose()
    }
}