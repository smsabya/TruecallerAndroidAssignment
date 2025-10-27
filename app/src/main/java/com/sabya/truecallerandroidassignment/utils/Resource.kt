package com.sabya.truecallerandroidassignment.utils

/**
 * A sealed class to represent different states of network response.
 * It helps manage UI feedback for loading, success, and error cases.
 */
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}