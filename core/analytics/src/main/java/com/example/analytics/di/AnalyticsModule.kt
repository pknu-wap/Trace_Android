package com.example.analytics.di

import com.example.analytics.AnalyticsHelper
import com.example.analytics.FirebaseAnalyticsHelper
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Provides
    @Singleton
    fun providesFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Provides
    @Singleton
    fun providesFirebaseCrashlytics(firebaseCrashlytics: FirebaseCrashlytics): FirebaseCrashlytics =
        Firebase.crashlytics

    @Provides
    @Singleton
    fun provideFirebaseAnalyticsHelper(
        firebaseAnalytics: FirebaseAnalytics,
        firebaseCrashlytics: FirebaseCrashlytics
    ): AnalyticsHelper = FirebaseAnalyticsHelper(firebaseAnalytics, firebaseCrashlytics)


}