package com.example.analytics.di

import com.example.analytics.AnalyticsHelper
import com.example.analytics.FirebaseAnalyticsHelper
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
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
    fun providesFirebaseAnalytics() : FirebaseAnalytics = Firebase.analytics

    @Provides
    @Singleton
    fun provideFirebaseAnalyticsHelper(firebaseAnalytics : FirebaseAnalytics) : AnalyticsHelper = FirebaseAnalyticsHelper(firebaseAnalytics)
}