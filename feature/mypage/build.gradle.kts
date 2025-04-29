plugins {
    id("trace.android.feature")
}

android {
    namespace = "com.example.mypage"
}

dependencies {
    implementation(libs.kakao.user)
    implementation(libs.coil.compose)
}
