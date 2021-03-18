# StarWarsSearch-Paging
An Android Application interacting with [Star Wars API](https://swapi.dev/). It enables users to search for Star Wars Characters using pagination and get their details from the Star Wars Universe.

## Features
* Modular Android App Architecture
* Clean Architecture + MVVM Architecture + Repository design Pattern.
* Jetpack Libraries and Architecture Component
* Kotlin Gradle DSL

## Architecture
Modular Android App Architecture presented in Google I/O’19, is a software design technique to separate functionality into independent, interchangeable modules so that each contains everything necessary to execute a specific functionality. Modular app architecture allows to be developed features in isolation, independently from other features.

Clean architecture helps organizing the project into different layers so that it's easy to understand, scale and debug when need arises.
Clean architecture maximizes the usage of SOLID principles. SOLID design principles intended to make software designs more understandable, flexible and maintainable. To keep things simple, I have used five layers :

* Presentation which is a layer that interacts with the UI.
* UseCases(Interactors) defines actions that user can trigger.
* Domain contains the business logic of the application.
* Data which include abstract definition of all the data sources.
* Framework which implements interaction with the Android SDK and provide concrete implementations for the data layer.

A single-activity architecture, using the Navigation component to manage fragment operations.

[Android architecture components](https://developer.android.com/topic/libraries/architecture/) which is part of Android Jetpack to give the project a robust design, testable and maintainable.

Pattern Model-View-ViewModel (MVVM) facilitating a separation of development of the graphical user interface.

## Modules

Modules are collection of source files and build settings that allow you to divide a project into discrete units of functionality. In this case apart from dividing by functionality/responsibility, existing the following dependence between them. The project is divided into 4 Modules :

#### App module

The `:app` module is an [com.android.application](https://developer.android.com/studio/build/), which is needed to create the app bundle. It is also responsible for initiating the [dependency graph](https://github.com/google/dagger).

#### Core module

The `:core` module is an [com.android.library](https://developer.android.com/studio/projects/android-library) for serving network requests. Providing the data source for the many features that require it.

#### Feature module

The `:features_search` module is an [com.android.dynamic-feature](https://developer.android.com/studio/projects/dynamic-delivery) is essentially a gradle module which can be downloaded independently from the base application module. It can hold code and resources and include dependencies, just like any other gradle module.

#### Commons module

The `:commons` module is an [com.android.library](https://developer.android.com/studio/projects/android-library) only contains code and resources which are shared between feature modules. Reusing this way resources, layouts, views, and components in the different features modules, without the need to duplicate code.

### Addenda

*  `:app`  depends on `:core` and indirectly depends on `:features_search` by dynamic-features. It also depends on `:commons` and `:feature_search` for instrumentation test using _androidTestImplementation_.
* `:features_search` modules depend on `:commons`, `:core` and `:app`.
* `:core` and `:commons` don’t have any dependency.

## Testing
Testing is done in each layer includes Repository and UseCase in `:core` module as well as ViewModel in `:features_search` module. This is one of the advantages of Clean Architecture.

# Libraries
* [Android Jetpack](https://developer.android.com/jetpack)
   * [Paging](https://developer.android.com/topic/libraries/architecture/paging) Library helps you load and display small chunks of data at a time. Loading partial data on demand reduces usage of network bandwidth and system resources.
   * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) ViewModel is designed to store and manage UI-related data in a lifecycle conscious way. This allows data to survive configuration changes such as screen rotations.
   * [DataBinding](https://developer.android.com/topic/libraries/data-binding/) is a Library in the support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
   * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) is lifecycle-aware, meaning it respects the lifecycle of other app components updating app component observers that are in an active lifecycle state.
   * [Navigation](https://developer.android.com/guide/navigation/) Android Jetpack's Navigation component helps you implement effective navigation.
* [Dynamic feature module](https://developer.android.com/guide/app-bundle/play-feature-delivery) allows you to separate certain features and resources from the base module of your app.
* [Dagger](https://developer.android.com/training/dependency-injection/dagger-multi-module) is a fully static, compile-time dependency injection framework for Java, Kotlin, and Android.
* [RxJava](https://github.com/ReactiveX/RxJava) is a library for composing asynchronous code using observable sequences.
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) is a module that adds the minimum classes to RxJava to make writing reactive components in Android.
* [Retrofit](https://square.github.io/retrofit/) is a Type-safe HTTP client for Android and Java and Kotlin by Square.
* [Moshi](https://github.com/square/moshi) is a modern JSON library for Android and Java. It makes it easy to parse JSON format data.
* [OkHttp interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) Logs HTTP requests and responses.
* [Material Design](https://material.io/develop/android/) Build beautiful, usable products using Material Components for Android
* [JUnit4](https://junit.org/junit4/) Unit Testing
* [Mockito](https://github.com/mockito/mockito) which is the most popular Mocking framework for unit tests written in Java as well as Kotlin.
* [Espresso](https://developer.android.com/training/testing/espresso) Automated testing UI test

# Licence
    MIT License

    Copyright (c) 2021 Mohammadali Rezaei

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
