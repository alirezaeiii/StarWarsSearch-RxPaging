package com.android.sample.starwars

import android.app.Application
import android.content.Context
import com.android.sample.core.di.ApplicationModule
import com.android.sample.core.di.CoreComponent
import com.android.sample.core.di.DaggerCoreComponent
import com.android.sample.starwars.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * An [Application] that uses Dagger for Dependency Injection.
 *
 * Also, sets up Timber in the DEBUG BuildConfig.
 */
open class StarWarsApplication : Application() {

    lateinit var coreComponent: CoreComponent

    companion object {

        /**
         * Obtain core dagger component.
         *
         * @param context The application context
         */
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as StarWarsApplication).coreComponent
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())

        /**
         * Initialize core dependency injection component.
         */
        coreComponent = DaggerCoreComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

        /**
         * Initialize app dependency injection component.
         */
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }
}