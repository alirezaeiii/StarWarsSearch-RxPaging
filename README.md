# StarWarsSearch-Paging
An Android Application interacting with [Star Wars API](https://swapi.dev/). It enabling users to search for Star Wars Characters and get their Details from the Star Wars Universe.

## Features
* Modular Android App Architecture
* Clean Architecture + MVVM Architecture + Repository design Pattern.
* Jetpack Libraries and Architecture Component
* Kotlin Gradle DSL

## Architecture
Modular Android App Architecture presented in Google I/O’19, is a software design technique to separate functionality into independent, interchangeable modules so that each contains everything necessary to execute a specific functionality.
Clean architecture helps organizing the project into different layers so that it's easy to understand, scale and debug when need arises.
Clean architecture maximizes the usa of SOLID principles. To keep things simple, I have used five layers.

* Presentation which is a layer that interacts with the UI.
* UseCases(InterActors) defines actions that user can trigger.
* Domain contains the business logic of the application.
* Data which include abstract definition of all the data sources.
* Framework which implements interaction with the Android SDK and provide concrete implementations for the data layer.

The project is divided into 4 Modules
* :app depends on :core and indirectly depends on :features_search by dynamic-features.
* :features modules depend on :commons, :core, :app.
* :core and *:commons don’t have any dependency.

## Testing
Testing is done in each layer which is one of the advantages of Clean Architecture.

# Libraries
* [Android Jetpack](https://developer.android.com/jetpack)
   * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) ViewModel is designed to store and manage UI-related data in a lifecycle conscious way. This allows data to survive configuration changes such as screen rotations.
   * [DataBinding](https://developer.android.com/topic/libraries/data-binding/) The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
   * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components updating app component observers that are in an active lifecycle state.
   * [Navigation](https://developer.android.com/guide/navigation/) Android Jetpack's Navigation component helps you implement effective navigation.
* [Dagger](https://developer.android.com/training/dependency-injection/dagger-multi-module) Dagger is a fully static, compile-time dependency injection framework for Java, Kotlin, and Android.
* [RxJava](https://github.com/ReactiveX/RxJava) RxJava is a library for composing asynchronous code using observable sequences.
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) RxAndroid is a module that adds the minimum classes to RxJava to make writing reactive components in Android.
* [Retrofit](https://square.github.io/retrofit/) Type-safe HTTP client for Android and Java and Kotlin by Square.
* [Moshi](https://github.com/square/moshi) Moshi is a modern JSON library for Android and Java. It makes it easy to parse JSON format data.
* [OkHttp interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) Logs HTTP requests and responses.
* [Material Design](https://material.io/develop/android/) Build beautiful, usable products using Material Components for Android
* [JUnit4](https://junit.org/junit4/) Unit Testing
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
