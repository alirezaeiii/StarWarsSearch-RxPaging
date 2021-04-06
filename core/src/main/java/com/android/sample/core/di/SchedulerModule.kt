package com.android.sample.core.di

import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.common.util.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class SchedulerModule {

    @Provides
    internal fun provideSchedulerProvider(): BaseSchedulerProvider =
            SchedulerProvider()
}