package com.sabya.truecallerandroidassignment.domain.usecase

import javax.inject.Inject

/**
 * Use case for finding the 15th character of text.
 */
class Truecaller15thCharacterUseCase @Inject constructor() {

    /**
     * @param text The input content string.
     * @return The 15th character if available, else null.
     */
    operator fun invoke(text: String): Char? = text.getOrNull(14)
}