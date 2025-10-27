package com.sabya.truecallerandroidassignment.data.repository

import com.sabya.truecallerandroidassignment.data.network.ApiService
import com.sabya.truecallerandroidassignment.domain.repository.ContentRepository
import com.sabya.truecallerandroidassignment.utils.Resource
import javax.inject.Inject

/**
 * Implementation of ContentRepository that fetches the content via Retrofit.
 */
class ContentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ContentRepository {

    /**
     * Fetches the blog content as plain text.
     * @return String - raw HTML or plain text content.
     */
    override suspend fun fetchContent(): Resource<String> {
        return try {
            val response = apiService.getContent()
            val body = response.body()?.toString().orEmpty()

            if (response.isSuccessful && body.isNotBlank()) {
                Resource.Success(body)
            } else {
                Resource.Error("Failed to load content from server.")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error occurred.")
        }
    }
}