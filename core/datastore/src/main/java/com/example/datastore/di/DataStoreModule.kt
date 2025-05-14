package com.example.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastore.datasource.keyword.LocalKeywordDataSource
import com.example.datastore.datasource.keyword.LocalKeywordDataSourceImpl
import com.example.datastore.datasource.token.LocalTokenDataSource
import com.example.datastore.datasource.token.LocalTokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreProvidesModule {
    private const val TOKEN_DATASTORE_NAME = "TOKEN_PREFERENCES"
    private val Context.tokenDataStore by preferencesDataStore(name = TOKEN_DATASTORE_NAME)

    private const val KEYWORD_DATASTORE_NAME = "KEYWORD_PREFERENCES"
    private val Context.keywordDataStore by preferencesDataStore(name = KEYWORD_DATASTORE_NAME)

    @Provides
    @Singleton
    @Named("token")
    fun provideTokenDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.tokenDataStore

    @Provides
    @Singleton
    @Named("keyword")
    fun provideKeywordDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.keywordDataStore

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DatastoreBindsModule {

    @Binds
    @Singleton
    abstract fun bindsLocalTokenDataSource(
        localTokenDataSourceImpl: LocalTokenDataSourceImpl,
    ): LocalTokenDataSource

    @Binds
    @Singleton
    abstract fun bindsLocalKeywordDataSource(
        localKeywordDataSourceImpl: LocalKeywordDataSourceImpl,
    ): LocalKeywordDataSource
}
