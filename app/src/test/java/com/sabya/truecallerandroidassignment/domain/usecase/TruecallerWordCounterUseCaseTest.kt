package com.sabya.truecallerandroidassignment.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class TruecallerWordCounterUseCaseTest {

    private val useCase = TruecallerWordCounterUseCase()

    @Test
    fun `should count words case-insensitively`() {
        val text = "Hello hello WORLD world"
        val result = useCase(text)
        assertEquals(mapOf("hello" to 2, "world" to 2), result)
    }

    @Test
    fun `should ignore blank words`() {
        val text = "   "
        val result = useCase(text)
        assertEquals(emptyMap<String, Int>(), result)
    }
}