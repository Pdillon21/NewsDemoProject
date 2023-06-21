package com.example.newsdemoproject.data.remote.repository

import com.example.newsdemoproject.data.remote.dto.container.GuardianResponseContainerDto

interface NewsRepository {

    suspend fun getGralArticles(): GuardianResponseContainerDto

    suspend fun getSingleArticleByID(articleID: String): GuardianResponseContainerDto

}
