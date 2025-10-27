package com.sabya.truecallerandroidassignment.di

import com.sabya.truecallerandroidassignment.data.network.ApiService
import com.sabya.truecallerandroidassignment.data.repository.ContentRepositoryImpl
import com.sabya.truecallerandroidassignment.domain.repository.ContentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://www.truecaller.com/blog/"

    /**
     * Provides a Retrofit instance used for making API calls.
     * Uses Scalar converter since we only need raw text from the endpoint.
     */
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create())
            .build()

    /**
     * Provides implementation of ApiService interface for network calls.
     */
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    /**
     * Provides an instance of [ContentRepository] to Hilt's dependency graph.
     *
     *  This function tells Hilt how to construct and provide a concrete implementation
     *  of the [ContentRepository] interface, using the [ContentRepositoryImpl] class
     */
    @Provides
    @Singleton
    fun provideContentRepository(
        apiService: ApiService
    ): ContentRepository = ContentRepositoryImpl(apiService)

}