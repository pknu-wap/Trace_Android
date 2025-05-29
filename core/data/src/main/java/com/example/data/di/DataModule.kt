package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.CommentRepositoryImpl
import com.example.data.repository.MissionRepositoryImpl
import com.example.data.repository.NotificationRepositoryImpl
import com.example.data.repository.PostRepositoryImpl
import com.example.data.repository.SearchRepositoryImpl
import com.example.data.repository.TokenManagerImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.CommentRepository
import com.example.domain.repository.MissionRepository
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.PostRepository
import com.example.domain.repository.SearchRepository
import com.example.domain.repository.UserRepository
import com.example.network.token.TokenManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindsNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsTokenManager(
        tokenManagerImpl: TokenManagerImpl,
    ): TokenManager

    @Binds
    @Singleton
    abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindsPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository

    @Binds
    @Singleton
    abstract fun bindsCommentRepository(
        commentRepositoryImpl: CommentRepositoryImpl
    ): CommentRepository


    @Binds
    @Singleton
    abstract fun bindsSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindsMissionRepository(
        missionRepositoryImpl: MissionRepositoryImpl
    ): MissionRepository
}
