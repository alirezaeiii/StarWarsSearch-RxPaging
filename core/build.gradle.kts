import dependencies.Dependencies
import extensions.addTestsDependencies

plugins {
    id("commons.android-library")
}

android {
    defaultConfig {
        buildConfigField("String", "STAR_WARS_API_BASE_URL", "\"https://swapi.dev/api/\"")
    }
}

dependencies {
    implementation(project(BuildModules.COMMON))

    api(Dependencies.LIFECYCLE_EXTENSIONS)
    api(Dependencies.FRAGMENT_KTX)
    implementation(Dependencies.NAVIGATION_UI)
    implementation(Dependencies.CORE_KTX)
    api(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_ADAPTER)
    implementation(Dependencies.RETROFIT_CONVERTER)
    implementation(Dependencies.RX_JAVA)
    implementation(Dependencies.LOGGING)
    implementation(Dependencies.MOSHI)
    implementation(Dependencies.MOSHI_KTX)
    implementation(Dependencies.TIMBER)

    addTestsDependencies()
}