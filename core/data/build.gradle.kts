plugins {
    id("trace.android.library")
    id("trace.android.hilt")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
}