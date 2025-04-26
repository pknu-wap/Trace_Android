plugins {
    id("trace.android.library")
    id("trace.android.compose")
}

android {
    namespace = "com.example.designsystem"
}

dependencies {
    implementation(projects.core.common)

    implementation(libs.coil.compose)
}


