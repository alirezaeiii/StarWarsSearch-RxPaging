package com.android.sample.feature.search

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.common.util.schedulers.ImmediateSchedulerProvider
import com.android.sample.core.domain.SearchPeopleUseCase
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.SearchRepository
import com.android.sample.core.response.Character
import com.android.sample.core.response.PeopleWrapper
import com.android.sample.feature.search.paging.SearchPageKeyRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class SearchPageKeyRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: StarWarsService

    @Mock
    private lateinit var context: Context

    private lateinit var schedulerProvider: BaseSchedulerProvider

    private val networkExecutor = Executor { command -> command.run() }

    @Before
    fun setup() {
        // Make the sure that all schedulers are immediate.
        schedulerProvider = ImmediateSchedulerProvider()
    }

    @Test
    fun searchPeople() {
        val searchRepository = SearchRepository(service)
        val searchPeopleUseCase = SearchPeopleUseCase(searchRepository)

        val searchPageKeyRepository = SearchPageKeyRepository(
                searchPeopleUseCase, "", CompositeDisposable(),
                schedulerProvider, context
        )

        val character = Character(
                "Ali", "127", "1385", emptyList(), emptyList()
        )

        val peopleWrapper = PeopleWrapper(listOf(character), null)

        `when`(service.searchPeople(anyString(), anyInt()))
                .thenReturn(Observable.just(peopleWrapper))

        val listing = searchPageKeyRepository.getItems(networkExecutor)
        val observer = LoggingObserver<PagedList<Character>>()
        listing.pagedList.observeForever(observer)

        observer.value.let {
            assertThat(it, `is`(notNullValue()))
            assertThat(it?.size, `is`(0))
        }
    }

    /**
     * simple observer that logs the latest value it receives
     */
    class LoggingObserver<T> : Observer<T> {
        var value: T? = null
        override fun onChanged(t: T?) {
            this.value = t
        }
    }
}