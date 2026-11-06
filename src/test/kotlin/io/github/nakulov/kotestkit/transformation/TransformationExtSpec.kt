package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.testing.runBehaviorSpec
import io.github.nakulov.kotestkit.testing.runStringSpec
import io.kotest.core.NamedTag
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.Enabled
import io.kotest.core.test.EnabledIf
import io.kotest.core.test.EnabledOrReasonIf
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestCaseSeverityLevel
import io.kotest.engine.test.TestResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

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

    "0–8 входов: blockingTest доходит до Kotest как написан" {
        val run = runStringSpec {
            transformationTest(0).config(testName = "0", blockingTest = true) { 0 }
            transformationTest(1, 1).config(testName = "1", blockingTest = true) { _ -> 1 }
            transformationTest(2, 1, 1).config(testName = "2", blockingTest = true) { _, _ -> 2 }
            transformationTest(3, 1, 1, 1).config(testName = "3", blockingTest = true) { _, _, _ -> 3 }
            transformationTest(4, 1, 1, 1, 1).config(testName = "4", blockingTest = true) { _, _, _, _ -> 4 }
            transformationTest(5, 1, 1, 1, 1, 1).config(testName = "5", blockingTest = true) { _, _, _, _, _ -> 5 }
            transformationTest(6, 1, 1, 1, 1, 1, 1).config(testName = "6", blockingTest = true) { _, _, _, _, _, _ -> 6 }
            transformationTest(7, 1, 1, 1, 1, 1, 1, 1).config(testName = "7", blockingTest = true) { _, _, _, _, _, _, _ -> 7 }
            transformationTest(8, 1, 1, 1, 1, 1, 1, 1, 1).config(testName = "8", blockingTest = true) { _, _, _, _, _, _, _, _ -> 8 }
        }

        run.tests.map { it.config?.blockingTest } shouldBe List(9) { true }
        run.tests.map { it.config?.coroutineTestScope } shouldBe List(9) { null }
        run.tests.map { it.config?.coroutineDebugProbes } shouldBe List(9) { null }
    }

    "0–8 входов: coroutineTestScope доходит до Kotest как написан" {
        val run = runStringSpec {
            transformationTest(0).config(testName = "0", coroutineTestScope = true) { 0 }
            transformationTest(1, 1).config(testName = "1", coroutineTestScope = true) { _ -> 1 }
            transformationTest(2, 1, 1).config(testName = "2", coroutineTestScope = true) { _, _ -> 2 }
            transformationTest(3, 1, 1, 1).config(testName = "3", coroutineTestScope = true) { _, _, _ -> 3 }
            transformationTest(4, 1, 1, 1, 1).config(testName = "4", coroutineTestScope = true) { _, _, _, _ -> 4 }
            transformationTest(5, 1, 1, 1, 1, 1).config(testName = "5", coroutineTestScope = true) { _, _, _, _, _ -> 5 }
            transformationTest(6, 1, 1, 1, 1, 1, 1).config(testName = "6", coroutineTestScope = true) { _, _, _, _, _, _ -> 6 }
            transformationTest(7, 1, 1, 1, 1, 1, 1, 1).config(testName = "7", coroutineTestScope = true) { _, _, _, _, _, _, _ -> 7 }
            transformationTest(8, 1, 1, 1, 1, 1, 1, 1, 1).config(testName = "8", coroutineTestScope = true) { _, _, _, _, _, _, _, _ -> 8 }
        }

        run.tests.map { it.config?.coroutineTestScope } shouldBe List(9) { true }
        run.tests.map { it.config?.blockingTest } shouldBe List(9) { null }
        run.tests.map { it.config?.coroutineDebugProbes } shouldBe List(9) { null }
    }

    "все настройки .config доходят до Kotest как написаны" {
        val tag = NamedTag("tag")
        val enabledIf: EnabledIf = { true }
        val enabledOrReasonIf: EnabledOrReasonIf = { Enabled.enabled }

        val run = runStringSpec {
            transformationTest(3, 1).config(
                testName = "name",
                enabled = true,
                invocations = 2,
                tags = setOf(tag),
                timeout = 1.minutes,
                enabledIf = enabledIf,
                invocationTimeout = 30.seconds,
                severity = TestCaseSeverityLevel.CRITICAL,
                enabledOrReasonIf = enabledOrReasonIf,
            ) { it + 2 }
        }

        val test = run.tests.single()
        val config = test.config!!
        test.name shouldBe "name"
        config.enabled shouldBe true
        config.invocations shouldBe 2
        config.tags shouldBe setOf(tag)
        config.timeout shouldBe 1.minutes
        config.enabledIf shouldBeSameInstanceAs enabledIf
        config.invocationTimeout shouldBe 30.seconds
        config.severity shouldBe TestCaseSeverityLevel.CRITICAL
        config.enabledOrReasonIf shouldBeSameInstanceAs enabledOrReasonIf
    }

    "enabled = false отключает тест" {
        val run = runStringSpec {
            transformationTest(3, 1).config(enabled = false) { it + 2 }
        }

        val test = run.tests.single()
        test.config?.enabled shouldBe false
        test.result.isIgnored shouldBe true
    }

    "расширение из extensions вызывается" {
        val calls = AtomicInteger()
        val extension = object : TestCaseExtension {
            override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
                calls.incrementAndGet()
                return execute(testCase)
            }
        }

        val run = runStringSpec {
            transformationTest(3, 1).config(extensions = listOf(extension)) { it + 2 }
        }

        run.tests.single().config?.extensions shouldBe listOf(extension)
        calls.get() shouldBe 1
    }
})
