package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.api.TraceApi
import com.example.network.authenticator.TraceAuthenticator
import com.example.network.interceptor.TraceInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: TraceInterceptor,
        authenticator: TraceAuthenticator,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .authenticator(authenticator)

        return builder.build()
    }

    @Singleton
    @Provides
    fun providesPieceApi(
        json: Json,
        okHttpClient: OkHttpClient,
    ): TraceApi = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BuildConfig.TRACE_BASE_URL)
        .build()
        .create(TraceApi::class.java)

}