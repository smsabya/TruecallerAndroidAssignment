package com.sabya.truecallerandroidassignment.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class Truecaller15thCharacterUseCaseTest {

    private val useCase = Truecaller15thCharacterUseCase()

    @Test
    fun `should return 15th character when available`() {
        val input = "1234567890123456"
        val result = useCase(input)
        assertEquals('5', result)
    }

    @Test
    fun `should return null when input too short`() {
        val input = "Hello"
        val result = useCase(input)
        assertEquals(null, result)
    }
}