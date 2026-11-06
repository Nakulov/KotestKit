package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.case
import io.github.nakulov.kotestkit.testing.runBehaviorSpec
import io.github.nakulov.kotestkit.testing.runStringSpec
import io.github.nakulov.kotestkit.withData
import io.kotest.core.NamedTag
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.Enabled
import io.kotest.core.test.EnabledIf
import io.kotest.core.test.EnabledOrReasonIf
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestCaseSeverityLevel
import io.kotest.core.test.config.TestConfig
import io.kotest.engine.test.TestResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class TransformationExtSpec : StringSpec({
    "StringSpec, 0–8 входов без options: каждый вызов регистрирует один успешный тест" {
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

    "StringSpec, 0–8 входов: options.name и blockingTest доходят до Kotest, тест один и успешный" {
        fun options(name: String) = TestOptions(name, TestConfig(blockingTest = true))

        val run = runStringSpec {
            transformationTest(0, options = options("0")) { 0 }
            transformationTest(1, 1, options = options("1")) { a -> a }
            transformationTest(2, 1, 1, options = options("2")) { a, b -> a + b }
            transformationTest(3, 1, 1, 1, options = options("3")) { a, b, c -> a + b + c }
            transformationTest(4, 1, 1, 1, 1, options = options("4")) { a, b, c, d -> a + b + c + d }
            transformationTest(5, 1, 1, 1, 1, 1, options = options("5")) { a, b, c, d, e -> a + b + c + d + e }
            transformationTest(6, 1, 1, 1, 1, 1, 1, options = options("6")) { a, b, c, d, e, f -> a + b + c + d + e + f }
            transformationTest(7, 1, 1, 1, 1, 1, 1, 1, options = options("7")) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
            transformationTest(8, 1, 1, 1, 1, 1, 1, 1, 1, options = options("8")) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
        }

        run.tests.map { it.name } shouldBe List(9) { "$it" }
        run.tests.map { it.result.isSuccess } shouldBe List(9) { true }
        run.tests.map { it.config?.blockingTest } shouldBe List(9) { true }
        run.tests.map { it.config?.coroutineTestScope } shouldBe List(9) { null }
        run.tests.map { it.config?.coroutineDebugProbes } shouldBe List(9) { null }
    }

    "BehaviorSpec, 0 и 1 вход: каждый вызов регистрирует один успешный тест" {
        val run = runBehaviorSpec {
            transformationTest(3) { 3 }
            transformationTest(3, 1) { input -> input + 2 }
            transformationTest(3, options = TestOptions("0 входов")) { 3 }
            transformationTest(3, 1, options = TestOptions("1 вход")) { input -> input + 2 }
        }

        run.tests.map { it.name } shouldBe listOf(
            "transformation to Int",
            "transformation Int to Int",
            "0 входов",
            "1 вход",
        )
        run.tests.map { it.result.isSuccess } shouldBe List(4) { true }
    }

    "без options используется имя по умолчанию" {
        val run = runStringSpec {
            transformationTest(0) { 0 }
            transformationTest(1, "abc") { 1 }
            transformationTest(2, 1, 1) { _, _ -> 2 }
            transformationTest(3, "a", 1, 1) { _, _, _ -> 3 }
        }

        run.tests.map { it.name } shouldBe listOf(
            "transformation to Int",
            "transformation String to Int",
            "transformation Int and Int to Int",
            "transformation String and Int and Int to Int",
        )
    }

    "TestOptions без имени оставляет имя по умолчанию" {
        val run = runStringSpec {
            transformationTest(0, options = TestOptions(config = TestConfig(blockingTest = true))) { 0 }
        }

        val test = run.tests.single()
        test.name shouldBe "transformation to Int"
        test.config?.blockingTest shouldBe true
    }

    "0–8 входов: тест падает при неверном ожидании" {
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

    "один вход и лямбда без объявленных параметров: один тест, и он падает" {
        val run = runStringSpec {
            transformationTest(999, 1) { 0 }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "BehaviorSpec: тест падает при неверном ожидании" {
        val run = runBehaviorSpec {
            transformationTest(999) { 3 }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "0–8 входов: coroutineTestScope доходит до Kotest как написан" {
        val options = TestOptions(config = TestConfig(coroutineTestScope = true))

        val run = runStringSpec {
            transformationTest(0, options = options) { 0 }
            transformationTest(1, 1, options = options) { _ -> 1 }
            transformationTest(2, 1, 1, options = options) { _, _ -> 2 }
            transformationTest(3, 1, 1, 1, options = options) { _, _, _ -> 3 }
            transformationTest(4, 1, 1, 1, 1, options = options) { _, _, _, _ -> 4 }
            transformationTest(5, 1, 1, 1, 1, 1, options = options) { _, _, _, _, _ -> 5 }
            transformationTest(6, 1, 1, 1, 1, 1, 1, options = options) { _, _, _, _, _, _ -> 6 }
            transformationTest(7, 1, 1, 1, 1, 1, 1, 1, options = options) { _, _, _, _, _, _, _ -> 7 }
            transformationTest(8, 1, 1, 1, 1, 1, 1, 1, 1, options = options) { _, _, _, _, _, _, _, _ -> 8 }
        }

        run.tests.map { it.config?.coroutineTestScope } shouldBe List(9) { true }
        run.tests.map { it.config?.blockingTest } shouldBe List(9) { null }
        run.tests.map { it.config?.coroutineDebugProbes } shouldBe List(9) { null }
    }

    "все настройки options доходят до Kotest как написаны" {
        val tag = NamedTag("tag")
        val enabledIf: EnabledIf = { true }
        val enabledOrReasonIf: EnabledOrReasonIf = { Enabled.enabled }

        val run = runStringSpec {
            transformationTest(
                3,
                1,
                options = TestOptions(
                    "name",
                    TestConfig(
                        enabled = true,
                        invocations = 2,
                        tags = setOf(tag),
                        timeout = 1.minutes,
                        enabledIf = enabledIf,
                        invocationTimeout = 30.seconds,
                        severity = TestCaseSeverityLevel.CRITICAL,
                        enabledOrReasonIf = enabledOrReasonIf,
                    ),
                ),
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
            transformationTest(3, 1, options = TestOptions(config = TestConfig(enabled = false))) { it + 2 }
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
            transformationTest(3, 1, options = TestOptions(config = TestConfig(extensions = listOf(extension)))) { it + 2 }
        }

        run.tests.single().config?.extensions shouldBe listOf(extension)
        calls.get() shouldBe 1
    }

    "withData: верное ожидание проверяется прямо в контексте, отдельного узла нет" {
        val run = runBehaviorSpec {
            withData(nameFn = { "c" }, case(1)) { _ ->
                transformationTest(3) { 3 }
            }
        }

        run.tests.single().result.isSuccess shouldBe true
    }

    "withData: неверное ожидание роняет контейнер" {
        val run = runBehaviorSpec {
            withData(nameFn = { "c" }, case(1)) { _ ->
                transformationTest(999) { 3 }
            }
        }

        run.tests.single().result.isFailure shouldBe true
    }
})
