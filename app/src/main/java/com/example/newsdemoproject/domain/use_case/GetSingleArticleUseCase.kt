package com.example.newsdemoproject.domain.use_case

import com.example.newsdemoproject.data.remote.common.NewsDemoResource
import com.example.newsdemoproject.data.remote.dto.container.GuardianResponseContainerDto
import com.example.newsdemoproject.data.remote.repository.NewsRepository
import com.example.newsdemoproject.data.remote.repository.NewsRepositoryImplementation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class GetSingleArticleUseCase(
    private val repository: NewsRepository = NewsRepositoryImplementation()
) {
    fun invoke(articleID : String): Flow<NewsDemoResource<GuardianResponseContainerDto>> =
        flow {
            try {
                emit(NewsDemoResource.Loading())
                val news = repository.getSingleArticleByID(articleID)
                emit(NewsDemoResource.Success(data = news))
            } catch (e: HttpException) {
                emit(NewsDemoResource.Error(e.localizedMessage))
            } catch (e: IOException) {
                emit(NewsDemoResource.Error("Check internet Connection"))
            }
        }
}