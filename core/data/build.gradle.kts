plugins {
    id("trace.android.library")
    id("trace.android.hilt")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.network)
}