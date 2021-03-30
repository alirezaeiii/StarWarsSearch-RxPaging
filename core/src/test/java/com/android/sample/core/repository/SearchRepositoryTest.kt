package com.android.sample.core.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.core.network.StarWarsService
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
class SearchRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: StarWarsService

    @Test
    fun searchRepository() {
        val searchRepository = SearchRepository(service)

        val peopleWrapper = CharacterWrapper(emptyList(), null)

        `when`(service.searchPeople(anyString(), anyInt()))
            .thenReturn(Observable.just(peopleWrapper))

        val testObserver = searchRepository.searchPeople(anyString(), anyInt()).test()

        testObserver.assertValues(peopleWrapper)

        testObserver.dispose()
    }
}