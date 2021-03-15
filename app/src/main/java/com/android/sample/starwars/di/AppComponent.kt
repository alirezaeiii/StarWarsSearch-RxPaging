package com.android.sample.starwars.di

import com.android.sample.core.di.AppScope
import com.android.sample.core.di.CoreComponent
import com.android.sample.starwars.StarWarsApplication
import dagger.Component

/**
 * App component that application component's components depend on.
 *
 * @see Component
 */
@AppScope
@Component(
    dependencies = [CoreComponent::class]
)
interface AppComponent {

    /**
     * Inject dependencies on application.
     *
     * @param application The sample application.
     */
    fun inject(application: StarWarsApplication)
}