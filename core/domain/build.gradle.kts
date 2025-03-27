plugins {
    id("trace.kotlin.library")
    id("trace.kotlin.hilt")
}

dependencies {
    dependencies {
        implementation(libs.coroutines.core)
        implementation(projects.core.common)
    }
}