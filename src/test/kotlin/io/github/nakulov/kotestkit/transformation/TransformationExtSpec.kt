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
