plugins {
    id("trace.kotlin.library")
    id("trace.kotlin.hilt")
}


dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.datetime)
}
