plugins {
    id("trace.android.application")
}

android {
    namespace = "com.example.trace"

    defaultConfig {
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(projects.feature.main)

    implementation(projects.core.designsystem)
}