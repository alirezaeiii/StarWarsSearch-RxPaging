package com.android.sample.core.di

import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.repository.DetailRepositoryImpl
import com.android.sample.core.repository.SearchRepository
import com.android.sample.core.repository.SearchRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    internal fun provideSearchRepository(service: StarWarsService): SearchRepository =
            SearchRepositoryImpl(service)

    @Provides
    internal fun provideDetailRepository(service: StarWarsService): DetailRepository =
            DetailRepositoryImpl(service)
}