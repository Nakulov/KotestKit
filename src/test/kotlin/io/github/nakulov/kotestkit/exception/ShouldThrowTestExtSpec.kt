package io.github.nakulov.kotestkit.exception

import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.case
import io.github.nakulov.kotestkit.testing.BEHAVIOR_CONTAINERS
import io.github.nakulov.kotestkit.testing.inEachContainer
import io.github.nakulov.kotestkit.testing.runBehaviorSpec
import io.github.nakulov.kotestkit.testing.runDescribeSpec
import io.github.nakulov.kotestkit.testing.runExpectSpec
import io.github.nakulov.kotestkit.testing.runFeatureSpec
import io.github.nakulov.kotestkit.testing.runFreeSpec
import io.github.nakulov.kotestkit.testing.runFunSpec
import io.github.nakulov.kotestkit.testing.runShouldSpec
import io.github.nakulov.kotestkit.testing.runStringSpec
import io.github.nakulov.kotestkit.testing.runWordSpec
import io.github.nakulov.kotestkit.withData
import io.kotest.core.NamedTag
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.Enabled
import io.kotest.core.test.EnabledIf
import io.kotest.core.test.EnabledOrReasonIf
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestCaseSeverityLevel
import io.kotest.core.test.TestType
import io.kotest.core.test.config.TestConfig
import io.kotest.engine.test.TestResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ShouldThrowTestExtSpec : StringSpec({
    "StringSpec, без options: каждый вызов регистрирует один успешный тест с именем по умолчанию" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException> { error("x") }
            shouldThrowWithMessageTest<IllegalStateException>("x") { error("x") }
            shouldThrowExactlyTest<IllegalStateException> { error("x") }
        }

        run.tests.map { it.name } shouldBe listOf(
            "should throw IllegalStateException",
            "should throw IllegalStateException with message \"x\"",
            "should throw IllegalStateException exactly",
        )
        run.tests.map { it.result.isSuccess } shouldBe List(3) { true }
    }

    "BehaviorSpec, без options: каждый вызов регистрирует один успешный тест" {
        val run = runBehaviorSpec {
            shouldThrowTest<IllegalStateException> { error("x") }
            shouldThrowWithMessageTest<IllegalStateException>("x") { error("x") }
            shouldThrowExactlyTest<IllegalStateException> { error("x") }
        }

        run.tests.map { it.result.isSuccess } shouldBe List(3) { true }
    }

    "options.name и blockingTest доходят до Kotest, тест один и успешный" {
        fun options(name: String) = TestOptions(name, TestConfig(blockingTest = true))

        val run = runStringSpec {
            shouldThrowTest<IllegalStateException>(options("0")) { error("x") }
            shouldThrowWithMessageTest<IllegalStateException>("x", options("1")) { error("x") }
            shouldThrowExactlyTest<IllegalStateException>(options("2")) { error("x") }
        }

        run.tests.map { it.name } shouldBe listOf("0", "1", "2")
        run.tests.map { it.result.isSuccess } shouldBe List(3) { true }
        run.tests.map { it.config?.blockingTest } shouldBe List(3) { true }
        run.tests.map { it.config?.coroutineTestScope } shouldBe List(3) { null }
        run.tests.map { it.config?.coroutineDebugProbes } shouldBe List(3) { null }
    }

    "TestOptions без имени оставляет имя по умолчанию" {
        val options = TestOptions(config = TestConfig(blockingTest = true))

        val run = runStringSpec {
            shouldThrowTest<IllegalStateException>(options) { error("x") }
            shouldThrowWithMessageTest<IllegalStateException>("x", options) { error("x") }
            shouldThrowExactlyTest<IllegalStateException>(options) { error("x") }
        }

        run.tests.map { it.name } shouldBe listOf(
            "should throw IllegalStateException",
            "should throw IllegalStateException with message \"x\"",
            "should throw IllegalStateException exactly",
        )
        run.tests.map { it.config?.blockingTest } shouldBe List(3) { true }
    }

    "StringSpec: тест падает, если исключения нет" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException> { }
            shouldThrowWithMessageTest<IllegalStateException>("x") { }
            shouldThrowExactlyTest<IllegalStateException> { }
        }

        run.tests.map { it.result.isFailure } shouldBe List(3) { true }
    }

    "BehaviorSpec: тест падает, если исключения нет" {
        val run = runBehaviorSpec {
            shouldThrowTest<IllegalStateException> { }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "coroutineTestScope доходит до Kotest как написан" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException>(TestOptions(config = TestConfig(coroutineTestScope = true))) {
                error("x")
            }
        }

        val config = run.tests.single().config
        config?.coroutineTestScope shouldBe true
        config?.blockingTest shouldBe null
        config?.coroutineDebugProbes shouldBe null
    }

    "все настройки options доходят до Kotest как написаны" {
        val tag = NamedTag("tag")
        val enabledIf: EnabledIf = { true }
        val enabledOrReasonIf: EnabledOrReasonIf = { Enabled.enabled }

        val run = runStringSpec {
            shouldThrowTest<IllegalStateException>(
                TestOptions(
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
            shouldThrowTest<IllegalStateException>(TestOptions(config = TestConfig(enabled = false))) { error("x") }
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
            shouldThrowTest<IllegalStateException>(TestOptions(config = TestConfig(extensions = listOf(extension)))) {
                error("x")
            }
        }

        run.tests.single().config?.extensions shouldBe listOf(extension)
        calls.get() shouldBe 1
    }

    "withData: shouldThrowTest и shouldThrowWithMessageTest создают успешные конечные тесты, когда блок бросает" {
        val run = runBehaviorSpec {
            withData(nameFn = { "c" }, case(1)) { _ ->
                shouldThrowTest<IllegalStateException> { error("x") }
                shouldThrowWithMessageTest<IllegalStateException>("x") { error("x") }
            }
        }

        val tests = run.childrenOf(listOf("c"))
        tests.map { it.name } shouldBe listOf(
            "should throw IllegalStateException",
            "should throw IllegalStateException with message \"x\"",
        )
        tests.map { it.result.isSuccess } shouldBe listOf(true, true)
    }

    "withData: shouldThrowTest и shouldThrowWithMessageTest роняют конечные тесты, а не контейнер, когда блок не бросает" {
        val run = runBehaviorSpec {
            withData(nameFn = { "c" }, case(1)) { _ ->
                shouldThrowTest<IllegalStateException> { }
                shouldThrowWithMessageTest<IllegalStateException>("x") { }
            }
        }

        run.childrenOf(listOf("c")).map { it.result.isFailure } shouldBe listOf(true, true)
        run.tests.single { it.path == listOf("c") }.result.isSuccess shouldBe true
    }

    "контейнеры BehaviorSpec: каждый вызов создаёт один успешный конечный тест" {
        val run = runBehaviorSpec {
            inEachContainer {
                shouldThrowTest<IllegalStateException> { error("x") }
                shouldThrowWithMessageTest<IllegalStateException>("x") { error("x") }
                shouldThrowExactlyTest<IllegalStateException> { error("x") }
            }
        }

        BEHAVIOR_CONTAINERS.forEach { path ->
            val tests = run.childrenOf(path)
            tests.map { it.name } shouldBe listOf(
                "should throw IllegalStateException",
                "should throw IllegalStateException with message \"x\"",
                "should throw IllegalStateException exactly",
            )
            tests.map { it.result.isSuccess } shouldBe List(3) { true }
            tests.map { it.type } shouldBe List(3) { TestType.Test }
            tests.map { it.prefix } shouldBe List(3) { null }
        }
    }

    "контейнер: options.name заменяет имя" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldThrowTest<IllegalStateException>(TestOptions("0")) { error("x") }
                shouldThrowWithMessageTest<IllegalStateException>("x", TestOptions("1")) { error("x") }
                shouldThrowExactlyTest<IllegalStateException>(TestOptions("2")) { error("x") }
            }
        }

        run.childrenOf(listOf("g")).map { it.name } shouldBe listOf("0", "1", "2")
    }

    "контейнер: у каждого вызова своя проверка" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldThrowTest<RuntimeException>(TestOptions("наследник подходит")) { error("x") }
                shouldThrowExactlyTest<RuntimeException>(TestOptions("наследник не подходит")) { error("x") }
                shouldThrowWithMessageTest<IllegalStateException>("y", TestOptions("другое сообщение")) { error("x") }
            }
        }

        run.childrenOf(listOf("g")).map { it.result.isSuccess } shouldBe listOf(true, false, false)
    }

    "контейнер: тест падает, если исключения нет" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldThrowTest<IllegalStateException> { }
                shouldThrowWithMessageTest<IllegalStateException>("x") { }
                shouldThrowExactlyTest<IllegalStateException> { }
            }
        }

        run.childrenOf(listOf("g")).map { it.result.isFailure } shouldBe List(3) { true }
    }

    "контейнер: падение проверки не отменяет следующую" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldThrowTest<IllegalStateException>(TestOptions("упавшая")) { }
                shouldThrowTest<IllegalStateException>(TestOptions("следующая")) { error("x") }
            }
        }

        val tests = run.childrenOf(listOf("g"))
        tests.map { it.name } shouldBe listOf("упавшая", "следующая")
        tests.map { it.result.isFailure } shouldBe listOf(true, false)
        tests.map { it.result.isSuccess } shouldBe listOf(false, true)
    }

    "контейнер: enabled = false отключает тест" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldThrowTest<IllegalStateException>(TestOptions(config = TestConfig(enabled = false))) { }
            }
        }

        val test = run.childrenOf(listOf("g")).single()
        test.config?.enabled shouldBe false
        test.result.isIgnored shouldBe true
    }

    "контейнер: blockingTest доходит до Kotest" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldThrowTest<IllegalStateException>(TestOptions(config = TestConfig(blockingTest = true))) { error("x") }
            }
        }

        run.childrenOf(listOf("g")).single().config?.blockingTest shouldBe true
    }

    "контейнер любого стиля: вызов создаёт один успешный конечный тест" {
        val runs = listOf(
            runFunSpec { context("c") { shouldThrowTest<IllegalStateException> { error("x") } } },
            runDescribeSpec { describe("c") { shouldThrowTest<IllegalStateException> { error("x") } } },
            runFreeSpec { "c" - { shouldThrowTest<IllegalStateException> { error("x") } } },
            runWordSpec { "c" should { shouldThrowTest<IllegalStateException> { error("x") } } },
            runShouldSpec { context("c") { shouldThrowTest<IllegalStateException> { error("x") } } },
            runExpectSpec { context("c") { shouldThrowTest<IllegalStateException> { error("x") } } },
            runFeatureSpec { feature("c") { shouldThrowTest<IllegalStateException> { error("x") } } },
        )

        runs.forEach { run ->
            val test = run.childrenOf(listOf("c")).single()
            test.name shouldBe "should throw IllegalStateException"
            test.result.isSuccess shouldBe true
            test.type shouldBe TestType.Test
            test.prefix shouldBe null
        }
    }
})
