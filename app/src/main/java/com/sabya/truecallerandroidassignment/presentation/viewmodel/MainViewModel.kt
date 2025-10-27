package com.sabya.truecallerandroidassignment.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabya.truecallerandroidassignment.domain.repository.ContentRepository
import com.sabya.truecallerandroidassignment.domain.usecase.Truecaller15thCharacterUseCase
import com.sabya.truecallerandroidassignment.domain.usecase.TruecallerEvery15thCharacterUseCase
import com.sabya.truecallerandroidassignment.domain.usecase.TruecallerWordCounterUseCase
import com.sabya.truecallerandroidassignment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel layer handles data operations for UI.
 * It coordinates fetching the content and executing all tasks concurrently.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ContentRepository,
    private val char15UseCase: Truecaller15thCharacterUseCase,
    private val every15UseCase: TruecallerEvery15thCharacterUseCase,
    private val wordCounterUseCase: TruecallerWordCounterUseCase
) : ViewModel() {

    val state = MutableLiveData<Resource<String>>()  // Tracks the main loading/success/error state
    val result1 = MutableLiveData<String>()
    val result2 = MutableLiveData<String>()
    val result3 = MutableLiveData<String>()

    /**
     * Fetches the content and processes it for the three tasks.
     * Updates the UI progressively for each stage of result completion.
     */
    fun performTasks() = viewModelScope.launch {
        state.postValue(Resource.Loading)

        when (val response = repository.fetchContent()) {
            is Resource.Success -> {
                state.postValue(Resource.Success("Data loaded successfully"))

                val content = response.data

                // Run tasks concurrently
                val task1 = async { char15UseCase(content)?.toString() ?: "N/A" }
                result1.postValue(task1.await())

                val task2 = async { every15UseCase(content).joinToString(", ") }
                result2.postValue(task2.await())

                val task3 = async {
                    wordCounterUseCase(content).entries.take(10)
                        .joinToString("\n") { "${it.key}: ${it.value}" }
                }
                result3.postValue(task3.await())
            }

            is Resource.Error -> {
                state.postValue(Resource.Error(response.message))
            }

            else -> Unit
        }
    }
}