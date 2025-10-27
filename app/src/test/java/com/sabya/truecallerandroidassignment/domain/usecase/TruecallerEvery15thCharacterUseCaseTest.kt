package com.sabya.truecallerandroidassignment.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class TruecallerEvery15thCharacterUseCaseTest {

    private val useCase = TruecallerEvery15thCharacterUseCase()

    @Test
    fun `should return every 15th character`() {
        val input = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val result = useCase(input)
        assertEquals(listOf('O', '4', 'I', 'X'), result)
    }

    @Test
    fun `should return empty list if string too short`() {
        val input = "shorttext"
        val result = useCase(input)
        assertEquals(emptyList<Char>(), result)
    }
}
