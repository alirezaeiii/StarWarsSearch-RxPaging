package com.android.sample.core.di

import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.repository.DetailRepositoryImpl
import com.android.sample.core.repository.SearchRepository
import com.android.sample.core.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Class that contributes to the object graph [CoreComponent].
 *
 * @see Module
 */
@Module
abstract class RepositoryModule {

    /**
     * Create a provider method binding for [SearchRepository].
     *
     * @return Instance of SearchRepositoryImpl.
     * @see Binds
     */
    @Singleton
    @Binds
    internal abstract fun bindSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    /**
     * Create a provider method binding for [DetailRepository].
     *
     * @return Instance of DetailRepositoryImpl.
     * @see Binds
     */
    @Singleton
    @Binds
    internal abstract fun bindDetailRepository(detailRepository: DetailRepositoryImpl): DetailRepository
}