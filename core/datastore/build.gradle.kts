plugins {
    id("trace.android.library")
    id("trace.android.hilt")
}

android {
    namespace = "com.puzzle.datastore"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.datastore)
    implementation(libs.gson)
}