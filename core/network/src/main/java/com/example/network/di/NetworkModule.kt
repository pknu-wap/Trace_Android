package com.example.network.di

import com.example.network.source.auth.AuthDataSource
import com.example.network.source.auth.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // binds 사용시 오류 떠서 임시로 provides..

    @Provides
    @Singleton
     fun providesAuthDataSource(): AuthDataSource {
         return AuthDataSourceImpl()
     }

}
