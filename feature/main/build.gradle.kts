plugins {
    id("trace.android.feature")
}

android {
    namespace = "com.example.main"

    defaultConfig {
        testInstrumentationRunner =
            "com.droidknights.app.core.testing.runner.DroidKnightsTestRunner"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.feature.auth)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)

}