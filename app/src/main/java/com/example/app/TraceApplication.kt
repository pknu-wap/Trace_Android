package com.example.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class TraceApplication : Application() {
    override fun onCreate() {
        super.onCreate()

//        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}