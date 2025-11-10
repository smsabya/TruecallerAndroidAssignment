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
import kotlinx.coroutines.Job
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

    val taskState =
        MutableLiveData<Resource<String>>()  // Tracks the main loading/success/error state
    val char15Result = MutableLiveData<String>()
    val every15Result = MutableLiveData<String>()
    val wordCounterResult = MutableLiveData<String>()

    private var fetchJob: Job? = null

    /**
     * Fetches the content and processes it for the three tasks.
     * Updates the UI progressively for each stage of result completion.
     */
    fun performTasks() {
        // Cancel previous job if running when button clicked again
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            taskState.postValue(Resource.Loading)

            when (val response = repository.fetchContent()) {
                is Resource.Success -> {
                    taskState.postValue(Resource.Success("Data loaded successfully"))

                    val content = response.data

                    // Run tasks concurrently
                    val char15Task = async { char15UseCase(content)?.toString() ?: "N/A" }
                    char15Result.postValue(char15Task.await())

                    val every15Task = async { every15UseCase(content).joinToString(", ") }
                    every15Result.postValue(every15Task.await())

                    val wordCounterTask = async {
                        wordCounterUseCase(content).entries.take(10)
                            .joinToString("\n") { "${it.key}: ${it.value}" }
                    }
                    wordCounterResult.postValue(wordCounterTask.await())
                }

                is Resource.Error -> {
                    taskState.postValue(Resource.Error(response.message))
                }

                else -> Unit
            }
        }
    }
}