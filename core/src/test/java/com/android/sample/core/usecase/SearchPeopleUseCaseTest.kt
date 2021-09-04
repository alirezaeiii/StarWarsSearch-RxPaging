package com.android.sample.core.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.SearchRepositoryImpl
import com.android.sample.core.response.CharacterWrapper
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPeopleUseCaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: StarWarsService

    @Test
    fun searchPeople() {
        val searchRepository = SearchRepositoryImpl(service)
        val searchPeopleUseCase = SearchPeopleUseCase(searchRepository)

        val characterWrapper = CharacterWrapper(emptyList(), null)

        `when`(service.searchPeople(anyString(), anyInt()))
            .thenReturn(Observable.just(characterWrapper))

        val testObserver = searchPeopleUseCase(anyString(), anyInt()).test()

        testObserver.assertValues(characterWrapper)

        testObserver.dispose()
    }
}