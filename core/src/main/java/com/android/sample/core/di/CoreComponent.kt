package com.android.sample.core.di

import com.android.sample.core.network.NetworkModule
import com.android.sample.core.network.StarWarsService
import com.android.sample.core.repository.SearchRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface CoreComponent {

    /**
     * Provide dependency graph StarWarsService
     *
     * @return StarWarsService
     */
    fun starWarsService(): StarWarsService

    /**
     * Provide dependency graph SearchRepository
     *
     * @return SearchRepository
     */
    fun searchRepository(): SearchRepository
}