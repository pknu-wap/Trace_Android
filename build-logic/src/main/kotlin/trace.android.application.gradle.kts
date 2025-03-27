import com.example.app.configureHiltAndroid
import com.example.app.configureKotestAndroid
import com.example.app.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
configureKotestAndroid()