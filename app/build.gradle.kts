plugins {
    id("trace.android.application")
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
}



android {
    namespace = "com.example.trace"

    defaultConfig {
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {
    implementation(projects.feature.main)

    implementation(libs.kakao.user)

    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.domain)
}