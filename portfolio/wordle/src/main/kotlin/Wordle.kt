// Implement the six required functions here
package wordle

import java.io.File
import kotlin.random.Random

fun isValid(word: String): Boolean = word.length == 5 && word.all { it.isLetter() }

fun readWordList(filename: String): MutableList<String> {
    val words = mutableListOf<String>()
    File(filename).forEachLine { line ->
        val word = line.trim().lowercase()
        if (isValid(word)) {
            words.add(word)
        }
    }
    return words
}

fun pickRandomWord(words: MutableList<String>): String {
    if (words.isEmpty()) throw IllegalArgumentException("Word list is empty!")
    val randomIndex = Random.nextInt(words.size)
    return words.removeAt(randomIndex)
}

fun obtainGuess(attempt: Int): String {
    while (true) {
        print("Attempt $attempt: ")
        val guess = readln().trim().lowercase()
        if (isValid(guess)) return guess
        println("Invalid guess. Please enter a 5-letter word.")
    }
}

fun evaluateGuess(guess: String, target: String): List<Int> {
    val result = MutableList(5) { 0 }
    val targetChars = target.toMutableList()

    for (i in guess.indices) {
        if (guess[i] == target[i]) {
            result[i] = 2
            targetChars[i] = '*'
        }
    }

    for (i in guess.indices) {
        if (result[i] == 0 && guess[i] in targetChars) {
            result[i] = 1
            targetChars[targetChars.indexOf(guess[i])] = '*'
        }
    }

    return result
}

fun displayGuess(guess: String, matches: List<Int>) {
    val reset = "\u001B[0m"
    val green = "\u001B[32m"
    val yellow = "\u001B[33m"

    for (i in guess.indices) {
        when (matches[i]) {
            2 -> print("$green${guess[i].uppercase()}$reset")
            1 -> print("$yellow${guess[i].uppercase()}$reset")
            else -> print("?")
        }
    }
    println()
}
