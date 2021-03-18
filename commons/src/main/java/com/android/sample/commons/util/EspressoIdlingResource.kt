/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.sample.commons.util

import androidx.test.espresso.IdlingResource
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Contains a static reference to [IdlingResource], only available in the 'mock' build type.
 */
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    EspressoIdlingResource.increment() // Set app as busy.
    return try {
        function()
    } finally {
        EspressoIdlingResource.decrement() // Set app as idle.
    }
}

inline fun <T> wrapEspressoIdlingResourceObservable(task: () -> Observable<T>): Observable<T> =
    task().doOnSubscribe { EspressoIdlingResource.increment() } // App is busy until further notice
        .doFinally { EspressoIdlingResource.decrement() } // Set app as idle.

inline fun <T> wrapEspressoIdlingResourceSingle(task: () -> Single<T>): Single<T> = task()
    .doOnSubscribe { EspressoIdlingResource.increment() } // App is busy until further notice
    .doFinally { EspressoIdlingResource.decrement() } // Set app as idle.