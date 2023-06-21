package com.example.newsdemoproject.data.remote.api

import com.example.newsdemoproject.data.remote.dto.container.GuardianResponseContainerDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GuardianApi {

    @GET("/search?show-fields=all")
    suspend fun getContent(): GuardianResponseContainerDto

    @GET("/{article_id}")
    suspend fun getSingleArticle(
        @Path("article_id") articleIdString: String
    ): GuardianResponseContainerDto

}
