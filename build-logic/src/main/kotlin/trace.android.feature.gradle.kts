import com.example.app.configureHiltAndroid
import com.example.app.libs
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("trace.android.library")
    id("trace.android.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

configureHiltAndroid()

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))
    implementation(project(":core:analytics"))


    val libs = project.extensions.libs
    implementation(libs.findLibrary("hilt-navigation-compose").get())
    implementation(libs.findLibrary("androidx.compose.navigation").get())

    implementation(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
    implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())

    androidTestImplementation(libs.findLibrary("androidx.compose.ui.test").get())
    debugImplementation(libs.findLibrary("androidx.compose.ui.test.manifest").get())

}