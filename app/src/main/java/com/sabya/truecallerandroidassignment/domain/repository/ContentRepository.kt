package com.sabya.truecallerandroidassignment.domain.repository

import com.sabya.truecallerandroidassignment.utils.Resource

/**
 * Repository interface that defines data-fetching responsibilities.
 */
interface ContentRepository {
    suspend fun fetchContent(): Resource<String>
}