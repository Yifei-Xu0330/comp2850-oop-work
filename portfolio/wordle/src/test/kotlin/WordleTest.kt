package wordle

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.io.File

@Suppress("unused")
class WordleTest : StringSpec({

    "isValid should only accept 5-letter alphabetic words" {
        withClue("should accept valid 5-letter words") {
            isValid("whoop") shouldBe true
            isValid("whose") shouldBe true
        }
        withClue("should reject invalid words") {
            isValid("who") shouldBe false
            isValid("widow1") shouldBe false
            isValid("widened") shouldBe false
        }
    }

    "readWordList should correctly read valid words from file" {
        val tempFile = File.createTempFile("words", ".txt")
        tempFile.writeText(
            """
            WHOOP
            WHOSE
            12345
            WIDEN
            WRONGWORD
            WIDER
            WIDOW
            """.trimIndent(),
        )

        val words = readWordList(tempFile.absolutePath)
        words shouldHaveSize 5
        words shouldContain "whoop"
        words shouldContain "whose"
        words shouldContain "widen"
        words shouldContain "wider"
        words shouldContain "widow"

        tempFile.delete()
    }

    "pickRandomWord should return and remove a random word" {
        val words = mutableListOf("whoop", "whose", "widen", "wider", "widow")
        val chosen = pickRandomWord(words)

        chosen.length shouldBe 5
        words.size shouldBe 4
        withClue("chosen word should no longer be in the list") {
            words.contains(chosen) shouldBe false
        }
    }

    "evaluateGuess should correctly evaluate guesses against target" {
        evaluateGuess("whoop", "whoop") shouldBe listOf(2, 2, 2, 2, 2)

        evaluateGuess("whose", "whoop") shouldBe listOf(2, 2, 2, 0, 0)

        evaluateGuess("widen", "wider") shouldBe listOf(2, 2, 2, 2, 0)

        evaluateGuess("whoop", "widen") shouldBe listOf(2, 0, 0, 0, 0)
    }
})
