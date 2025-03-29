import java.io.FileInputStream
import java.util.Properties

plugins {
    id("trace.android.feature")
}

android {
    namespace = "com.example.main"

    defaultConfig {
        val properties = Properties().apply {
            load(FileInputStream(rootProject.file("local.properties")))
        }

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            properties["KAKAO_NATIVE_APP_KEY"] as String
        )

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties["KAKAO_NATIVE_APP_KEY"] as String
    }

    buildFeatures {
        buildConfig = true
    }


}

dependencies {
    implementation(projects.feature.auth)

    implementation(libs.kakao.user)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)

}