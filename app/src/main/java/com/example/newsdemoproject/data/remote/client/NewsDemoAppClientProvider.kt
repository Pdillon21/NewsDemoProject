package com.example.newsdemoproject.data.remote.client

import com.example.newsdemoproject.BuildConfig
import com.example.newsdemoproject.utils.log.NewsDemoProjectLogger
import java.lang.Exception
import kotlin.jvm.Throws
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsDemoAppClientProvider {

    private lateinit var httpClient: Retrofit
    private var baseUrl: String? = null

    fun init(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    fun getClient(): Retrofit {
        if (!this::httpClient.isInitialized) buildClient()
        return httpClient
    }

    private fun buildClient() {
        baseUrl?.let { urlString ->
            NewsDemoProjectLogger.log("ClientBuild PreState", "baseUrl:$baseUrl")
            try {
                httpClient = Retrofit.Builder().client(buildHttpClient()).baseUrl(urlString)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            } catch (e: Exception) {
                throw Exception("HttpClientUtils Exception, buildClient Exception, missing baseUrl")
            }
        }
    }

    private fun buildHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(getApiKeyInterceptor()).build()
    }

    private fun getApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl: HttpUrl = originalRequest.url
            val newUrl: HttpUrl =
                originalUrl.newBuilder().addQueryParameter("api-key", getApiKey()).build()
            val newRequest: Request = originalRequest.newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        }
    }

    @Throws
    private fun getApiKey(): String {
        return BuildConfig.API_KEY
    }
}
