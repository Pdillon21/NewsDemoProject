package com.example.newsdemoproject.domain.use_case

import com.example.newsdemoproject.data.remote.common.NewsDemoResource
import com.example.newsdemoproject.data.remote.dto.container.GuardianResponseContainerDto
import com.example.newsdemoproject.data.remote.repository.NewsRepository
import com.example.newsdemoproject.data.remote.repository.NewsRepositoryImplementation
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetGeneralArticlesUseCase(
    private val repository: NewsRepository = NewsRepositoryImplementation()
) {

    fun invoke(): Flow<NewsDemoResource<GuardianResponseContainerDto>> =
        flow {
            try {
                emit(NewsDemoResource.Loading())
                val news = repository.getGralArticles()
                emit(NewsDemoResource.Success(data = news))
            } catch (e: HttpException) {
                emit(NewsDemoResource.Error(e.localizedMessage))
            } catch (e: IOException) {
                emit(NewsDemoResource.Error("Check internet Connection"))
            }
        }
}
