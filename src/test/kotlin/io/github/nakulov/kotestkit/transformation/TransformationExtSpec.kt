package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.testing.runBehaviorSpec
import io.github.nakulov.kotestkit.testing.runStringSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TransformationExtSpec : StringSpec({
    "StringSpec: каждый вызов регистрирует по одному успешному тесту" {
        val run = runStringSpec {
            transformationTest(3) { 3 }
            transformationTest(3, 1) { input -> input + 2 }
            transformationTest(3, 1).config(testName = "builder") { input -> input + 2 }
        }

        run.tests.map { it.result.isSuccess } shouldBe listOf(true, true, true)
    }

    "BehaviorSpec: каждый вызов регистрирует по одному успешному тесту" {
        val run = runBehaviorSpec {
            transformationTest(3) { 3 }
            transformationTest(3, 1) { input -> input + 2 }
            transformationTest(3, 1).config(testName = "builder") { input -> input + 2 }
        }

        run.tests.map { it.result.isSuccess } shouldBe listOf(true, true, true)
    }

    "лямбда на 0–8 входов: каждый вызов регистрирует один успешный тест" {
        val run = runStringSpec {
            transformationTest(0) { 0 }
            transformationTest(1, 1) { a -> a }
            transformationTest(2, 1, 1) { a, b -> a + b }
            transformationTest(3, 1, 1, 1) { a, b, c -> a + b + c }
            transformationTest(4, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
            transformationTest(5, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
            transformationTest(6, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
            transformationTest(7, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
            transformationTest(8, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
        }

        run.tests.map { it.result.isSuccess } shouldBe List(9) { true }
    }

    "лямбда на 0–8 входов: тест падает при неверном ожидании" {
        val run = runStringSpec {
            transformationTest(999) { 0 }
            transformationTest(999, 1) { a -> a }
            transformationTest(999, 1, 1) { a, b -> a + b }
            transformationTest(999, 1, 1, 1) { a, b, c -> a + b + c }
            transformationTest(999, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
            transformationTest(999, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
            transformationTest(999, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
            transformationTest(999, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
            transformationTest(999, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
        }

        run.tests.map { it.result.isFailure } shouldBe List(9) { true }
    }

    "StringSpec: тест падает при неверном ожидании" {
        val run = runStringSpec {
            transformationTest(999) { 3 }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "BehaviorSpec: тест падает при неверном ожидании" {
        val run = runBehaviorSpec {
            transformationTest(999) { 3 }
        }

        run.tests.single().result.isFailure shouldBe true
    }
})
