package com.sabya.truecallerandroidassignment.domain.usecase

import javax.inject.Inject

/**
 * Use case for extracting every 15th character from content.
 */
class TruecallerEvery15thCharacterUseCase @Inject constructor() {

    /**
     * @param text The input string.
     * @return A list containing every 15th character.
     */
    operator fun invoke(text: String): List<Char> {
        val result = mutableListOf<Char>()
        // Start at index 14 (the 15th character) and take steps of 15
        for (i in 14 until text.length step 15) {
            result.add(text[i])
        }
        return result
    }
}