package com.sabya.truecallerandroidassignment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sabya.truecallerandroidassignment.domain.repository.ContentRepository
import com.sabya.truecallerandroidassignment.domain.usecase.Truecaller15thCharacterUseCase
import com.sabya.truecallerandroidassignment.domain.usecase.TruecallerEvery15thCharacterUseCase
import com.sabya.truecallerandroidassignment.domain.usecase.TruecallerWordCounterUseCase
import com.sabya.truecallerandroidassignment.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    // Ensures LiveData events happen instantly and synchronously in tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    // Mock dependencies
    private val repository: ContentRepository = mockk()
    private val char15UseCase = Truecaller15thCharacterUseCase()
    private val every15UseCase = TruecallerEvery15thCharacterUseCase()
    private val wordUseCase = TruecallerWordCounterUseCase()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repository, char15UseCase, every15UseCase, wordUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `performTasks posts correct results for success response`() = testScope.runTest {
        val content = "<p>Truecaller Hello World </p>"
        coEvery { repository.fetchContent() } returns Resource.Success(content)

        viewModel.performTasks()
        // Move coroutine scheduler to let all pending work finish
        advanceUntilIdle()

        val result = viewModel.result1.getOrAwaitValue()
        assertEquals(char15UseCase(content)?.toString() ?: "N/A", result)
    }

    @Test
    fun `performTasks posts error state for error response`() = runBlocking {
        coEvery { repository.fetchContent() } returns Resource.Error("Network Error")

        viewModel.performTasks()
        val stateResult = viewModel.state.getOrAwaitValue()
        assertEquals(Resource.Error("Network Error"), stateResult)
    }

    /**
     * Extension function to reliably get a LiveData value or wait for it with timeout.
     */
    private fun <T> androidx.lifecycle.LiveData<T>.getOrAwaitValue(
        time: Long = 2, timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        // Wait for LiveData to emit a value
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("LiveData value was never set.")
        }
        return data!!
    }
}