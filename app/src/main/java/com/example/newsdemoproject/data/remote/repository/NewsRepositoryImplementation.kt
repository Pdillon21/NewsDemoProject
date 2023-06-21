package com.example.newsdemoproject.data.remote.repository

import com.example.newsdemoproject.data.remote.api.ApiProvider
import com.example.newsdemoproject.data.remote.api.GuardianApi
import com.example.newsdemoproject.data.remote.dto.container.GuardianResponseContainerDto
import com.example.newsdemoproject.utils.log.NewsDemoProjectLogger

class NewsRepositoryImplementation(
    private val api: GuardianApi = ApiProvider.getGuardianApi()
) : NewsRepository {

    override suspend fun getGralArticles(): GuardianResponseContainerDto {
        NewsDemoProjectLogger.log("${this.javaClass}", "Run getGralArticles")
        return api.getContent()
    }

    override suspend fun getSingleArticleByID(articleId : String): GuardianResponseContainerDto {
        NewsDemoProjectLogger.log("${this.javaClass}", "Run getSingleArticle")
        return api.getSingleArticle(articleId)
    }
}
