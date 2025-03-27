plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "trace.android.hilt"
            implementationClass = "com.example.trace.build.logic.HiltAndroidPlugin"
        }
        register("kotlinHilt") {
            id = "trace.kotlin.hilt"
            implementationClass = "com.example.trace.build.logic.HiltKotlinPlugin"
        }
    }
}