import com.example.app.configureCoroutineAndroid
import com.example.app.configureHiltAndroid
import com.example.app.configureKotest
import com.example.app.configureKotlinAndroid


plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureHiltAndroid()
configureKotest()
configureCoroutineAndroid()
