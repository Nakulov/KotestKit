package io.github.nakulov.kotestkit

import io.github.nakulov.kotestkit.testing.runBehaviorSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DataExtSpec : StringSpec({
    "1–8 входов: все аргументы доходят до блока в своём порядке" {
        val seen = mutableListOf<List<String>>()

        runBehaviorSpec {
            withData(nameFn = { "$it" }, case("1")) { a ->
                seen += listOf(a)
            }
            withData(nameFn = { "$it" }, case("1", "2")) { a, b ->
                seen += listOf(a, b)
            }
            withData(nameFn = { "$it" }, case("1", "2", "3")) { a, b, c ->
                seen += listOf(a, b, c)
            }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4")) { a, b, c, d ->
                seen += listOf(a, b, c, d)
            }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4", "5")) { a, b, c, d, e ->
                seen += listOf(a, b, c, d, e)
            }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4", "5", "6")) { a, b, c, d, e, f ->
                seen += listOf(a, b, c, d, e, f)
            }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4", "5", "6", "7")) { a, b, c, d, e, f, g ->
                seen += listOf(a, b, c, d, e, f, g)
            }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4", "5", "6", "7", "8")) { a, b, c, d, e, f, g, h ->
                seen += listOf(a, b, c, d, e, f, g, h)
            }
        }

        seen shouldBe (1..8).map { size -> (1..size).map(Int::toString) }
    }

    "1–8 входов: на каждый случай один контейнер с именем из nameFn" {
        val run = runBehaviorSpec {
            withData(nameFn = { "$it" }, case("1"), case("p")) { _ -> }
            withData(nameFn = { "$it" }, case("1", "2"), case("p", "q")) { _, _ -> }
            withData(nameFn = { "$it" }, case("1", "2", "3"), case("p", "q", "r")) { _, _, _ -> }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4"), case("p", "q", "r", "s")) { _, _, _, _ -> }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4", "5"), case("p", "q", "r", "s", "t")) { _, _, _, _, _ -> }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4", "5", "6"), case("p", "q", "r", "s", "t", "u")) { _, _, _, _, _, _ -> }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4", "5", "6", "7"), case("p", "q", "r", "s", "t", "u", "v")) { _, _, _, _, _, _, _ -> }
            withData(nameFn = { "$it" }, case("1", "2", "3", "4", "5", "6", "7", "8"), case("p", "q", "r", "s", "t", "u", "v", "w")) { _, _, _, _, _, _, _, _ -> }
        }

        run.tests.map { it.name } shouldBe EXPECTED_NAMES
        run.tests.map { it.result.isSuccess } shouldBe List(16) { true }
    }
})

private val EXPECTED_NAMES = (1..8).flatMap { size ->
    listOf(
        caseName((1..size).map(Int::toString)),
        caseName(('p' until 'p' + size).map(Char::toString)),
    )
}

private fun caseName(args: List<String>): String =
    args.withIndex().joinToString(", ", "Case${args.size}(", ")") { (index, arg) -> "arg${index + 1}=$arg" }
