plugins {
    id("trace.android.feature")
}

android {
    namespace = "com.example.home"

}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
}