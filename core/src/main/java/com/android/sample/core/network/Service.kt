package com.android.sample.core.network

import com.android.sample.core.BuildConfig
import com.android.sample.core.model.PeopleWrapper
import com.android.sample.core.repository.SearchRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import javax.inject.Singleton

/**
 * A retrofit service to fetch items.
 */
interface StarWarsService {

    @GET("people/")
    fun searchPeople(@Query("search") query: String): Single<PeopleWrapper>
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun getLoggerInterceptor(): Interceptor {
    val logger = HttpLoggingInterceptor {
        Timber.d(it)
    }
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

/**
 * Main entry point for network access.
 */
@Module
class NetworkModule {

    // Configure retrofit to parse JSON and use rxJava
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.STAR_WARS_API_BASE_URL)
        .client(OkHttpClient.Builder()
                .addInterceptor(getLoggerInterceptor())
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideStarWarsService(retrofit: Retrofit): StarWarsService =
        retrofit.create(StarWarsService::class.java)

    @Singleton
    @Provides
    fun provideRepository(service: StarWarsService) = SearchRepository(service)
}

