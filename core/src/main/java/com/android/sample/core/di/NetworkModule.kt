package com.android.sample.core.di

import com.android.sample.core.BuildConfig
import com.android.sample.core.network.StarWarsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

/**
 * Main entry point for network access.
 */
@Module
class NetworkModule {

    // Configure retrofit to parse JSON and use rxJava
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        /**
         * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
         * full Kotlin compatibility.
         */
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        val logger = HttpLoggingInterceptor {
            Timber.d(it)
        }
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient =  OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.STAR_WARS_API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideStarWarsService(retrofit: Retrofit): StarWarsService =
            retrofit.create(StarWarsService::class.java)
}