package com.example.network.di

import com.example.network.source.auth.AuthDataSource
import com.example.network.source.auth.AuthDataSourceImpl
import com.example.network.source.post.PostDataSource
import com.example.network.source.post.PostDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindsAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindsPostDataSource(postDataSourceImpl: PostDataSourceImpl): PostDataSource
}
