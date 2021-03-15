import dependencies.Dependencies

plugins {
    id("commons.android-library")
}

dependencies {
    api(Dependencies.APPCOMPAT)
    api(Dependencies.CORE_KTX)
    implementation(Dependencies.RX_JAVA)
    implementation(Dependencies.RX_ANDROID)
    implementation(Dependencies.TIMBER)
    implementation("androidx.paging:paging-runtime-ktx:2.1.2")
}