plugins {
    id("trace.android.library")
    id("trace.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.network"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp.logging)
}