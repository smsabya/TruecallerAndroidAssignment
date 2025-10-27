package com.sabya.truecallerandroidassignment.data.repository

import com.sabya.truecallerandroidassignment.data.network.ApiService
import com.sabya.truecallerandroidassignment.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response

class ContentRepositoryImplTest {

    private val apiService = mockk<ApiService>()
    private val repository = ContentRepositoryImpl(apiService)

    @Test
    fun `should return Success when api returns valid response`() = runBlocking {
        coEvery { apiService.getContent() } returns Response.success("Sample text")
        val result = repository.fetchContent()
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `should return Error when api fails`() = runBlocking {
        coEvery { apiService.getContent() } returns Response.error(
            404, ResponseBody.create(null, "Error")
        )
        val result = repository.fetchContent()
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `should return Error when exception occurs`() = runBlocking {
        coEvery { apiService.getContent() } throws Exception("Network failure")
        val result = repository.fetchContent()
        assertTrue(result is Resource.Error)
    }
}