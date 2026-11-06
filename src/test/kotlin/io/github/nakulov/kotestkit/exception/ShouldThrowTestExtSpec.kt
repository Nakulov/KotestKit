package io.github.nakulov.kotestkit.exception

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

class ShouldThrowTestExtSpec : StringSpec({
    "StringSpec: каждый вызов регистрирует по одному успешному тесту" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException> { error("x") }
            shouldThrowTest<IllegalStateException>().config(testName = "builder") { error("x") }
            shouldThrowWithMessageTest<IllegalStateException>("x") { error("x") }
            shouldThrowExactlyTest<IllegalStateException> { error("x") }
        }

        run.tests.map { it.result.isSuccess } shouldBe listOf(true, true, true, true)
    }

    "BehaviorSpec: каждый вызов регистрирует по одному успешному тесту" {
        val run = runBehaviorSpec {
            shouldThrowTest<IllegalStateException> { error("x") }
            shouldThrowTest<IllegalStateException>().config(testName = "builder") { error("x") }
            shouldThrowWithMessageTest<IllegalStateException>("x") { error("x") }
            shouldThrowExactlyTest<IllegalStateException> { error("x") }
        }

        run.tests.map { it.result.isSuccess } shouldBe listOf(true, true, true, true)
    }

    "StringSpec: тест падает, если исключения нет" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException> { }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "BehaviorSpec: тест падает, если исключения нет" {
        val run = runBehaviorSpec {
            shouldThrowTest<IllegalStateException> { }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "blockingTest доходит до Kotest как написан" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException>().config(blockingTest = true) { error("x") }
        }

        val config = run.tests.single().config
        config?.blockingTest shouldBe true
        config?.coroutineTestScope shouldBe null
        config?.coroutineDebugProbes shouldBe null
    }

    "coroutineTestScope доходит до Kotest как написан" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException>().config(coroutineTestScope = true) { error("x") }
        }

        val config = run.tests.single().config
        config?.coroutineTestScope shouldBe true
        config?.blockingTest shouldBe null
        config?.coroutineDebugProbes shouldBe null
    }

    "все настройки .config доходят до Kotest как написаны" {
        val tag = NamedTag("tag")
        val enabledIf: EnabledIf = { true }
        val enabledOrReasonIf: EnabledOrReasonIf = { Enabled.enabled }

        val run = runStringSpec {
            shouldThrowTest<IllegalStateException>().config(
                testName = "name",
                enabled = true,
                invocations = 2,
                tags = setOf(tag),
                timeout = 1.minutes,
                enabledIf = enabledIf,
                invocationTimeout = 30.seconds,
                severity = TestCaseSeverityLevel.CRITICAL,
                enabledOrReasonIf = enabledOrReasonIf,
            ) { error("x") }
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
            shouldThrowTest<IllegalStateException>().config(enabled = false) { error("x") }
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
            shouldThrowTest<IllegalStateException>().config(extensions = listOf(extension)) { error("x") }
        }

        run.tests.single().config?.extensions shouldBe listOf(extension)
        calls.get() shouldBe 1
    }
})
