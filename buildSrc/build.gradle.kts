plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

object PluginsVersions {
    const val GRADLE_ANDROID = "4.1.2"
    const val KOTLIN = "1.4.21"
    const val NAVIGATION = "2.3.4"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_ANDROID}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.NAVIGATION}")
}