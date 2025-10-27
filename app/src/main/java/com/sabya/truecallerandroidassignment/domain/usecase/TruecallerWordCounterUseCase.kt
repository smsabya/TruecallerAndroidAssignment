package com.sabya.truecallerandroidassignment.domain.usecase

import javax.inject.Inject

/**
 * Use case for counting occurrences of each unique word in the text.
 * Case insensitive and whitespace-aware.
 */
class TruecallerWordCounterUseCase @Inject constructor() {

    /**
     * @param text The content to be analyzed.
     * @return A map of each word and its occurrence count.
     */
    operator fun invoke(text: String): Map<String, Int> {
        val wordCountMap = mutableMapOf<String, Int>()

        // Split the string by spaces, tabs, or line breaks
        val words = text.split("\\s+".toRegex())

        // Loop through every word
        for (word in words) {
            if (word.isNotBlank()) {
                val lowerWord = word.lowercase()
                // Increment count or initialize with 1 if first encounter
                wordCountMap[lowerWord] = wordCountMap.getOrDefault(lowerWord, 0) + 1
            }
        }

        return wordCountMap
    }
}