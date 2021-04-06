package com.android.sample.core.di

import android.app.Application
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.DetailRepository
import com.android.sample.core.repository.SearchRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(modules = [ApplicationModule::class,
    NetworkModule::class,
    RepositoryModule::class]
)
interface CoreComponent {

    /**
     * Provide dependency graph Application
     *
     * @return Application
     */
    fun application(): Application

    /**
     * Provide dependency graph StarWarsService
     *
     * @return StarWarsService
     */
    fun starWarsService(): StarWarsService

    /**
     * Provide dependency graph SchedulerProvider
     *
     * @return BaseSchedulerProvider
     */
    fun schedulerProvider(): BaseSchedulerProvider

    /**
     * Provide dependency graph SearchRepositoryImpl
     *
     * @return SearchRepository
     */
    fun searchRepository(): SearchRepository

    /**
     * Provide dependency graph DetailRepositoryImpl
     *
     * @return DetailRepository
     */
    fun detailRepository(): DetailRepository
}