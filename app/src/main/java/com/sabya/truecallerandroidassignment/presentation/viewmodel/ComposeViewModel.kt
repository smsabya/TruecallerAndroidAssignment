package com.sabya.truecallerandroidassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabya.truecallerandroidassignment.domain.repository.ContentRepository
import com.sabya.truecallerandroidassignment.domain.usecase.Truecaller15thCharacterUseCase
import com.sabya.truecallerandroidassignment.domain.usecase.TruecallerEvery15thCharacterUseCase
import com.sabya.truecallerandroidassignment.domain.usecase.TruecallerWordCounterUseCase
import com.sabya.truecallerandroidassignment.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val repository: ContentRepository,
    private val char15UseCase: Truecaller15thCharacterUseCase,
    private val every15UseCase: TruecallerEvery15thCharacterUseCase,
    private val wordCounterUseCase: TruecallerWordCounterUseCase
) : ViewModel() {

    private var fetchJob: Job? = null

    private val _taskState = MutableStateFlow<Resource<String>?>(null)
    val taskState: StateFlow<Resource<String>> get() = _taskState as StateFlow<Resource<String>>

    private val _char15Result = MutableStateFlow("")
    val char15Result: StateFlow<String> get() = _char15Result

    private val _every15Result = MutableStateFlow("")
    val every15Result: StateFlow<String> get() = _every15Result

    private val _wordCounterResult = MutableStateFlow("")
    val wordCounterResult: StateFlow<String> get() = _wordCounterResult

    fun performTasks() {

        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            _taskState.value = Resource.Loading

            when (val response = repository.fetchContent()) {
                is Resource.Success -> {
                    _taskState.value = Resource.Success("Data loaded successfully")

                    val content = response.data

                    _char15Result.value = char15UseCase(content)?.toString() ?: "N/A"
                    _every15Result.value = every15UseCase(content).joinToString(", ")
                    _wordCounterResult.value = wordCounterUseCase(content).entries.take(10)
                        .joinToString("\n") { "${it.key}: ${it.value}" }
                }

                is Resource.Error -> {
                    _taskState.value = Resource.Error(response.message)
                }

                else -> {
                    // no-op
                }
            }
        }
    }
}
