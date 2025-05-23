plugins {
    id("trace.android.feature")
}

android {
    namespace = "com.example.mission"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
}

