package com.example.network.authenticator

import android.util.Log
import com.example.network.api.TraceApi
import com.example.network.model.token.RefreshTokenRequest
import com.example.network.token.TokenManager
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TraceAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val traceApi: Provider<TraceApi>
) : Authenticator {
    private val refreshMutex = Mutex()
    override fun authenticate(route: Route?, response: Response): Request? {
        val originRequest = response.request


        if (originRequest.header("Authorization").isNullOrEmpty()) {
            return null
        }

        if (originRequest.url.encodedPath.contains("/api/v1/token/refresh")) {
            runBlocking {
                tokenManager.setAccessToken("")
                tokenManager.setRefreshToken("")
            }
            return null
        }

        if (response.code != 401) {
            return null
        }

        val retryCount = originRequest.header(RETRY_HEADER)?.toIntOrNull() ?: 0
        if (retryCount >= MAX_RETRY_COUNT) {
            return null
        }

        val token = runBlocking {
            refreshMutex.withLock {
                traceApi.get().refreshToken(RefreshTokenRequest(tokenManager.getRefreshToken()))
            }
        }.getOrNull() ?: return null

        Log.d("refreshToken", "토큰 재발급 성공 ${token}")

        runBlocking {
            val accessTokenJob = launch { tokenManager.setAccessToken(token.accessToken) }
            val refreshTokenJob = launch { tokenManager.setRefreshToken(token.refreshToken) }
            joinAll(accessTokenJob, refreshTokenJob)
        }

        val newRequest = response.request
            .newBuilder()
            .header("Authorization", "Bearer ${token.accessToken}")
            .header(RETRY_HEADER, (retryCount + 1).toString())
            .build()

        return newRequest
    }

    companion object {
        private const val MAX_RETRY_COUNT = 3
        private const val RETRY_HEADER = "Retry-Count"
    }

}