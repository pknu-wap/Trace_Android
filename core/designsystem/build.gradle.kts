plugins {
    id("trace.android.library")
    id("trace.android.compose")
}

android {
    namespace = "com.example.designsystem"
}

dependencies {
    implementation(libs.coil.compose)
}


