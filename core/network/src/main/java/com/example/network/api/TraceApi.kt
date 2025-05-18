package com.example.network.api

import com.example.network.model.auth.LoginKakaoRequest
import com.example.network.model.auth.LoginKakaoResponse
import com.example.network.model.auth.TokenResponse
import com.example.network.model.comment.AddCommentRequest
import com.example.network.model.comment.AddReplyToCommentRequest
import com.example.network.model.comment.CommentResponse
import com.example.network.model.post.AddPostResponse
import com.example.network.model.post.GetPostResponse
import com.example.network.model.post.UpdatePostRequest
import com.example.network.model.post.UpdatePostResponse
import com.example.network.model.token.CheckTokenHealthResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface TraceApi {
    @POST("/api/v1/auth/oauth/login")
    suspend fun loginKakao(@Body loginKakaoRequest: LoginKakaoRequest): Result<LoginKakaoResponse>

    @Multipart
    @POST("/api/v1/auth/oauth/signup")
    suspend fun registerUser(
        @Part("request") registerUserRequest: RequestBody,
        @Part profileImage: MultipartBody.Part? = null
    ): Result<TokenResponse>

    @HTTP(method = "GET", path = "/api/v1/token/refresh")
    suspend fun refreshToken(@Query("refreshToken") refreshToken: String): Result<TokenResponse>

    @GET("/api/v1/token/expiration")
    suspend fun checkTokenHealth(@Query("token") token: String): Result<CheckTokenHealthResponse>

    @GET("/api/v1/posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Result<GetPostResponse>

    @Multipart
    @POST("/api/v1/posts")
    suspend fun addPost(
        @Part("request") addPostRequest: RequestBody,
        @Part imageFile: List<MultipartBody.Part>? = null
    ): Result<AddPostResponse>

    @PUT("/api/v1/posts/{id}")
    suspend fun updatePost(
        @Path("id") postId: Int,
        @Body updatePostRequest: UpdatePostRequest
    ): Result<UpdatePostResponse>

    @DELETE("/api/v1/posts/{id}")
    suspend fun deletePost(
        @Path("id") postId: Int,
    ): Result<Unit>

    @POST("/api/v1/comments/{postId}")
    suspend fun addComment(
        @Path("postId") postId: Int,
        @Body addCommentRequest: AddCommentRequest,
    ): Result<CommentResponse>

    @POST("/api/v1/comments/{postId}/{commentId}")
    suspend fun addReplyToComment(
        @Path("postId") postId: Int,
        @Path("commentId") commentId: Int,
        @Body addReplyToCommentRequest: AddReplyToCommentRequest,
    ): Result<CommentResponse>

    @DELETE("/api/v1/comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: Int,
    ): Result<Unit>

}
