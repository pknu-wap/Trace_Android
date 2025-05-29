package com.example.network.di

import com.example.network.source.auth.AuthDataSource
import com.example.network.source.auth.AuthDataSourceImpl
import com.example.network.source.comment.CommentDataSource
import com.example.network.source.comment.CommentDataSourceImpl
import com.example.network.source.mission.MissionDataSource
import com.example.network.source.mission.MissionDataSourceImpl
import com.example.network.source.notification.NotificationDataSource
import com.example.network.source.notification.NotificationDataSourceImpl
import com.example.network.source.post.PostDataSource
import com.example.network.source.post.PostDataSourceImpl
import com.example.network.source.user.UserDataSource
import com.example.network.source.user.UserDataSourceImpl
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindsNotificationDataSource(notificationDataSourceImpl: NotificationDataSourceImpl): NotificationDataSource

    @Binds
    @Singleton
    abstract fun bindsAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindsUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    abstract fun bindsPostDataSource(postDataSourceImpl: PostDataSourceImpl): PostDataSource

    @Binds
    @Singleton
    abstract fun bindsCommentDataSource(commentDataSourceImpl: CommentDataSourceImpl): CommentDataSource

    @Binds
    @Singleton
    abstract fun bindsMissionDataSource(missionDataSourceImpl: MissionDataSourceImpl): MissionDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvidesModule {
    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()

}

