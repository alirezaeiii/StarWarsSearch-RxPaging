package com.android.sample.core.di

import android.app.Application
import com.android.sample.core.network.StarWarsService
import dagger.Component
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(modules = [ApplicationModule::class,
    NetworkModule::class]
)
interface CoreComponent {

    /**
     * Provide dependency graph Context
     *
     * @return Context
     */
    fun application(): Application

    /**
     * Provide dependency graph StarWarsService
     *
     * @return StarWarsService
     */
    fun starWarsService(): StarWarsService
}