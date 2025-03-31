package com.example.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsHelper @Inject constructor(
    private val firebaseAnalytics : FirebaseAnalytics
) : AnalyticsHelper() {
    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.type, event.toBundle())
    }

    private fun AnalyticsEvent.toBundle(): Bundle {
        val bundle = Bundle()
        properties?.forEach { (key, value) ->
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Float -> bundle.putFloat(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                else -> {}
            }
        }
        return bundle
    }

}