package wordle  

fun main() {
    val words = readWordList("data/words.txt")
    val target = pickRandomWord(words)
    println("Welcome to Wordle!")
    println("You have up to 6 attempts to guess the word.\n")

    for (attempt in 1..6) {
        val guess = obtainGuess(attempt)
        val matches = evaluateGuess(guess, target)
        displayGuess(guess, matches)
        if (matches.all { it == 2 }) {
            println("Congratulations! You guessed the word '$target' in $attempt attempts.")
            return
        }
    }

    println("You've run out of guesses. The word was '$target'. Better luck next time!")
}

