package com.sabya.truecallerandroidassignment.data.network

import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit API Interface defining all endpoints for remote network calls.
 * In this app, we are fetching a blog page as plain text content.
 */
interface ApiService {

    /**
     * Fetch the content of the Truecaller blog post.
     * Returns the plain text content from the website.
     */
    @GET("life-at-truecaller/life-as-an-android-engineer")
    suspend fun getContent(): Response<String>
}