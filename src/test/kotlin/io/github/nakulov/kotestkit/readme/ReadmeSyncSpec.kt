package io.github.nakulov.kotestkit.readme

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import java.io.File

class ReadmeSyncSpec : StringSpec({
    "каждый пример из README есть в спеке" {
        fun normalize(text: String) = "\n" + text.trim().lines().joinToString("\n") { it.trim() } + "\n"
        val examples = File("src/test/kotlin/io/github/nakulov/kotestkit/readme").walk()
            .filter { it.extension == "kt" && it.name != "ReadmeSyncSpec.kt" }
            .joinToString("\n") { normalize(it.readText()) }
        val blocks = Regex("```kotlin\\n(.*?)```", RegexOption.DOT_MATCHES_ALL)
            .findAll(File("README.md").readText())
            .map { it.groupValues[1] }
            .toList()

        blocks.shouldNotBeEmpty()
        blocks.filterNot { normalize(it) in examples }.shouldBeEmpty()
    }
})
