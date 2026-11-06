package io.github.nakulov.kotestkit

import io.github.nakulov.kotestkit.testing.BEHAVIOR_CONTAINERS
import io.github.nakulov.kotestkit.testing.RanTest
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
import io.kotest.core.NamedTag
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.Enabled
import io.kotest.core.test.EnabledIf
import io.kotest.core.test.EnabledOrReasonIf
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestCaseSeverityLevel
import io.kotest.core.test.TestType
import io.kotest.core.test.config.TestConfig
import io.kotest.engine.test.TestResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class ShouldReturnTestExtSpec : StringSpec({
    "StringSpec, 0–8 входов без options: каждый вызов регистрирует один успешный тест" {
        val run = runStringSpec {
            shouldReturnTest(0) { 0 }
            shouldReturnTest(1, 1) { a -> a }
            shouldReturnTest(2, 1, 1) { a, b -> a + b }
            shouldReturnTest(3, 1, 1, 1) { a, b, c -> a + b + c }
            shouldReturnTest(4, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
            shouldReturnTest(5, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
            shouldReturnTest(6, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
            shouldReturnTest(7, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
            shouldReturnTest(8, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
        }

        run.tests.map { it.result.isSuccess } shouldBe List(9) { true }
    }

    "StringSpec, 0–8 входов: options.name и blockingTest доходят до Kotest, тест один и успешный" {
        fun options(name: String) = TestOptions(name, TestConfig(blockingTest = true))

        val run = runStringSpec {
            shouldReturnTest(0, options = options("0")) { 0 }
            shouldReturnTest(1, 1, options = options("1")) { a -> a }
            shouldReturnTest(2, 1, 1, options = options("2")) { a, b -> a + b }
            shouldReturnTest(3, 1, 1, 1, options = options("3")) { a, b, c -> a + b + c }
            shouldReturnTest(4, 1, 1, 1, 1, options = options("4")) { a, b, c, d -> a + b + c + d }
            shouldReturnTest(5, 1, 1, 1, 1, 1, options = options("5")) { a, b, c, d, e -> a + b + c + d + e }
            shouldReturnTest(6, 1, 1, 1, 1, 1, 1, options = options("6")) { a, b, c, d, e, f -> a + b + c + d + e + f }
            shouldReturnTest(7, 1, 1, 1, 1, 1, 1, 1, options = options("7")) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
            shouldReturnTest(8, 1, 1, 1, 1, 1, 1, 1, 1, options = options("8")) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
        }

        run.tests.map { it.name } shouldBe List(9) { "$it" }
        run.tests.map { it.result.isSuccess } shouldBe List(9) { true }
        run.tests.map { it.config?.blockingTest } shouldBe List(9) { true }
        run.tests.map { it.config?.coroutineTestScope } shouldBe List(9) { null }
        run.tests.map { it.config?.coroutineDebugProbes } shouldBe List(9) { null }
    }

    "BehaviorSpec, 0 и 1 вход: каждый вызов регистрирует один успешный тест" {
        val run = runBehaviorSpec {
            shouldReturnTest(3) { 3 }
            shouldReturnTest(3, 1) { input -> input + 2 }
            shouldReturnTest(3, options = TestOptions("0 входов")) { 3 }
            shouldReturnTest(3, 1, options = TestOptions("1 вход")) { input -> input + 2 }
        }

        run.tests.map { it.name } shouldBe listOf(
            "expected 3",
            "1 -> 3",
            "0 входов",
            "1 вход",
        )
        run.tests.map { it.result.isSuccess } shouldBe List(4) { true }
    }

    "корень, 0–8 входов: имя по умолчанию показывает значения, порядок входов сохранён" {
        val run = runStringSpec {
            shouldReturnTest(0) { 0 }
            shouldReturnTest(1, "a") { 1 }
            shouldReturnTest(2, "a", 1) { _, _ -> 2 }
            shouldReturnTest(3, "a", 1, null) { _, _, _ -> 3 }
            shouldReturnTest(4, 1, 2, 3, 4) { _, _, _, _ -> 4 }
            shouldReturnTest(5, 1, 2, 3, 4, 5) { _, _, _, _, _ -> 5 }
            shouldReturnTest(6, 1, 2, 3, 4, 5, 6) { _, _, _, _, _, _ -> 6 }
            shouldReturnTest(7, 1, 2, 3, 4, 5, 6, 7) { _, _, _, _, _, _, _ -> 7 }
            shouldReturnTest(8, 1, 2, 3, 4, 5, 6, 7, 8) { _, _, _, _, _, _, _, _ -> 8 }
        }

        run.tests.map { it.name } shouldBe listOf(
            "expected 0",
            "\"a\" -> 1",
            "(\"a\", 1) -> 2",
            "(\"a\", 1, <null>) -> 3",
            "(1, 2, 3, 4) -> 4",
            "(1, 2, 3, 4, 5) -> 5",
            "(1, 2, 3, 4, 5, 6) -> 6",
            "(1, 2, 3, 4, 5, 6, 7) -> 7",
            "(1, 2, 3, 4, 5, 6, 7, 8) -> 8",
        )
    }

    "TestOptions без имени оставляет имя по умолчанию" {
        val run = runStringSpec {
            shouldReturnTest(0, options = TestOptions(config = TestConfig(blockingTest = true))) { 0 }
        }

        val test = run.tests.single()
        test.name shouldBe "expected 0"
        test.config?.blockingTest shouldBe true
    }

    "0–8 входов: тест падает при неверном ожидании" {
        val run = runStringSpec {
            shouldReturnTest(999) { 0 }
            shouldReturnTest(999, 1) { a -> a }
            shouldReturnTest(999, 1, 1) { a, b -> a + b }
            shouldReturnTest(999, 1, 1, 1) { a, b, c -> a + b + c }
            shouldReturnTest(999, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
            shouldReturnTest(999, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
            shouldReturnTest(999, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
            shouldReturnTest(999, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
            shouldReturnTest(999, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
        }

        run.tests.map { it.result.isFailure } shouldBe List(9) { true }
    }

    "один вход и лямбда без объявленных параметров: один тест, и он падает" {
        val run = runStringSpec {
            shouldReturnTest(999, 1) { 0 }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "BehaviorSpec: тест падает при неверном ожидании" {
        val run = runBehaviorSpec {
            shouldReturnTest(999) { 3 }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "0–8 входов: coroutineTestScope доходит до Kotest как написан" {
        val options = TestOptions(config = TestConfig(coroutineTestScope = true))

        val run = runStringSpec {
            shouldReturnTest(0, options = options) { 0 }
            shouldReturnTest(1, 1, options = options) { _ -> 1 }
            shouldReturnTest(2, 1, 1, options = options) { _, _ -> 2 }
            shouldReturnTest(3, 1, 1, 1, options = options) { _, _, _ -> 3 }
            shouldReturnTest(4, 1, 1, 1, 1, options = options) { _, _, _, _ -> 4 }
            shouldReturnTest(5, 1, 1, 1, 1, 1, options = options) { _, _, _, _, _ -> 5 }
            shouldReturnTest(6, 1, 1, 1, 1, 1, 1, options = options) { _, _, _, _, _, _ -> 6 }
            shouldReturnTest(7, 1, 1, 1, 1, 1, 1, 1, options = options) { _, _, _, _, _, _, _ -> 7 }
            shouldReturnTest(8, 1, 1, 1, 1, 1, 1, 1, 1, options = options) { _, _, _, _, _, _, _, _ -> 8 }
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
            shouldReturnTest(
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
            shouldReturnTest(3, 1, options = TestOptions(config = TestConfig(enabled = false))) { it + 2 }
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
            shouldReturnTest(3, 1, options = TestOptions(config = TestConfig(extensions = listOf(extension)))) { it + 2 }
        }

        run.tests.single().config?.extensions shouldBe listOf(extension)
        calls.get() shouldBe 1
    }

    "withData: верное ожидание создаёт успешный конечный тест" {
        val run = runBehaviorSpec {
            withData(nameFn = { "c" }, case(1)) { _ ->
                shouldReturnTest(3) { 3 }
            }
        }

        val test = run.childrenOf(listOf("c")).single()
        test.name shouldBe "expected 3"
        test.result.isSuccess shouldBe true
    }

    "withData: неверное ожидание роняет конечный тест, а не контейнер" {
        val run = runBehaviorSpec {
            withData(nameFn = { "c" }, case(1)) { _ ->
                shouldReturnTest(999) { 3 }
            }
        }

        run.childrenOf(listOf("c")).single().result.isFailure shouldBe true
        run.tests.single { it.path == listOf("c") }.result.isSuccess shouldBe true
    }

    "контейнеры BehaviorSpec, 0–8 входов: каждый вызов создаёт один успешный конечный тест" {
        val run = runBehaviorSpec {
            inEachContainer { allForms() }
        }

        BEHAVIOR_CONTAINERS.forEach { path ->
            val tests = run.childrenOf(path)
            tests.map { it.name } shouldBe DEFAULT_NAMES
            tests.map { it.result.isSuccess } shouldBe List(9) { true }
            tests.map { it.type } shouldBe List(9) { TestType.Test }
            tests.map { it.prefix } shouldBe List(9) { null }
        }
    }

    "контейнер: имя по умолчанию показывает значения входов, null как <null>" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldReturnTest(0, "a", 1, null) { _, _, _ -> 0 }
            }
        }

        run.childrenOf(listOf("g")).single().name shouldBe "(\"a\", 1, <null>) -> 0"
    }

    "контейнер, 0–8 входов: options.name заменяет имя" {
        val run = runBehaviorSpec {
            Given("g") {
                allForms { TestOptions("$it") }
            }
        }

        run.childrenOf(listOf("g")).map { it.name } shouldBe List(9) { "$it" }
    }

    "контейнер, 0–8 входов: тест падает при неверном ожидании" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldReturnTest(999) { 0 }
                shouldReturnTest(999, 1) { a -> a }
                shouldReturnTest(999, 1, 1) { a, b -> a + b }
                shouldReturnTest(999, 1, 1, 1) { a, b, c -> a + b + c }
                shouldReturnTest(999, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
                shouldReturnTest(999, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
                shouldReturnTest(999, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
                shouldReturnTest(999, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
                shouldReturnTest(999, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
            }
        }

        run.childrenOf(listOf("g")).map { it.result.isFailure } shouldBe List(9) { true }
    }

    "контейнер, 0 и 1 вход: testCase в блоке — сам тест, а не контейнер" {
        val names = mutableListOf<String>()

        val run = runFunSpec {
            context("c") {
                shouldReturnTest(0) {
                    names += testCase.name.name
                    0
                }
                shouldReturnTest(1, 1) { a ->
                    names += testCase.name.name
                    a
                }
            }
        }

        run.childrenOf(listOf("c")).map { it.result.isSuccess } shouldBe listOf(true, true)
        names shouldBe listOf("expected 0", "1 -> 1")
    }

    "контейнер: падение проверки не отменяет следующую" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldReturnTest(999, options = TestOptions("упавшая")) { 0 }
                shouldReturnTest(0, options = TestOptions("следующая")) { 0 }
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
                shouldReturnTest(999, options = TestOptions(config = TestConfig(enabled = false))) { 0 }
            }
        }

        val test = run.childrenOf(listOf("g")).single()
        test.config?.enabled shouldBe false
        test.result.isIgnored shouldBe true
    }

    "контейнер: blockingTest доходит до Kotest" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldReturnTest(0, options = TestOptions(config = TestConfig(blockingTest = true))) { 0 }
            }
        }

        run.childrenOf(listOf("g")).single().config?.blockingTest shouldBe true
    }

    "контейнер любого стиля: вызов создаёт один успешный конечный тест" {
        val runs = listOf(
            runFunSpec { context("c") { shouldReturnTest(0) { 0 } } },
            runDescribeSpec { describe("c") { shouldReturnTest(0) { 0 } } },
            runFreeSpec { "c" - { shouldReturnTest(0) { 0 } } },
            runWordSpec { "c" should { shouldReturnTest(0) { 0 } } },
            runShouldSpec { context("c") { shouldReturnTest(0) { 0 } } },
            runExpectSpec { context("c") { shouldReturnTest(0) { 0 } } },
            runFeatureSpec { feature("c") { shouldReturnTest(0) { 0 } } },
        )

        runs.forEach { run ->
            val test = run.childrenOf(listOf("c")).single()
            test.name shouldBe "expected 0"
            test.result.isSuccess shouldBe true
            test.type shouldBe TestType.Test
            test.prefix shouldBe null
        }
    }

    "корень и контейнер: разные значения в одном скоупе не дают номеров" {
        val run = runBehaviorSpec {
            shouldReturnTest(1, "a") { it.length }
            shouldReturnTest(1, "b") { it.length }
            Given("g") {
                shouldReturnTest(1, "a") { it.length }
                shouldReturnTest(2, "ab") { it.length }
            }
        }

        run.tests.filter { it.type == TestType.Test }.map { it.name } shouldBe listOf(
            "\"a\" -> 1",
            "\"b\" -> 1",
            "\"a\" -> 1",
            "\"ab\" -> 2",
        )
    }

    "полностью одинаковые вызовы сохраняют номер от Kotest" {
        val run = runStringSpec {
            shouldReturnTest(1, "a") { it.length }
            shouldReturnTest(1, "a") { it.length }
        }

        run.tests.map { it.name } shouldBe listOf("\"a\" -> 1", "(1) \"a\" -> 1")
    }

    "значения показываются без адресов объектов" {
        val lambda: () -> Int = { 1 }
        val suspendLambda: suspend () -> Int = { 1 }
        val cases = listOf<Pair<Any?, String>>(
            "abc" to "\"abc\"",
            3 to "3",
            Color.RED to "RED",
            User(1) to "User(id=1)",
            listOf(1, 2) to "[1, 2]",
            Repo() to "Repo",
            Holder(Repo()) to "Holder(repo=Repo)",
            listOf(Repo()) to "[Repo]",
            object {} to "<anonymous>",
            lambda to "<lambda>",
            "" to "<empty string>",
            null to "<null>",
            suspendLambda to "() -> kotlin.Int",
            ::same to "fun same(kotlin.Int): kotlin.Int",
            "user@abc" to "\"user@abc\"",
            "com.acme.Foo@abc" to "\"com.acme.Foo@abc\"",
        )

        val run = runStringSpec {
            cases.forEach { (value, _) -> shouldReturnTest(0, value) { _ -> 0 } }
        }

        lambda::class.java.name shouldContain "\$\$Lambda"
        run.tests.map { it.name } shouldBe cases.map { (_, shown) -> "$shown -> 0" }
    }

    "значение длиннее 40 символов обрезается, кавычки считаются" {
        val run = runStringSpec {
            shouldReturnTest(0, "a".repeat(38)) { _ -> 0 }
            shouldReturnTest(0, "a".repeat(39)) { _ -> 0 }
            shouldReturnTest("b".repeat(100)) { "b".repeat(100) }
        }

        run.tests.map { it.name } shouldBe listOf(
            "\"${"a".repeat(38)}\" -> 0",
            "\"${"a".repeat(39)}… -> 0",
            "expected \"${"b".repeat(39)}…",
        )
    }

    "вход с бросающим toString: спека не падает, в имени имя класса, следующий тест выполняется" {
        val throwing = object {
            override fun toString(): String = error("x")
        }

        val run = runStringSpec {
            shouldReturnTest(0, ThrowingToString()) { _ -> 0 }
            shouldReturnTest(0, CyclicToString()) { _ -> 0 }
            shouldReturnTest(0, throwing) { _ -> 0 }
            shouldReturnTest(0, "следующий") { _ -> 0 }
        }

        run.specError shouldBe null
        run.tests.map { it.name } shouldBe listOf(
            "ThrowingToString -> 0",
            "CyclicToString -> 0",
            "<anonymous> -> 0",
            "\"следующий\" -> 0",
        )
        run.tests.map { it.result.isSuccess } shouldBe List(4) { true }
    }

    "падение в корне, 0–8 входов: первый кадр стека — файл спеки и строка вызова" {
        val lines = mutableListOf<Int>()

        val run = runStringSpec {
            lines += nextLine()
            shouldReturnTest(999) { 0 }
            lines += nextLine()
            shouldReturnTest(999, 1) { a -> a }
            lines += nextLine()
            shouldReturnTest(999, 1, 1) { a, b -> a + b }
            lines += nextLine()
            shouldReturnTest(999, 1, 1, 1) { a, b, c -> a + b + c }
            lines += nextLine()
            shouldReturnTest(999, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
            lines += nextLine()
            shouldReturnTest(999, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
            lines += nextLine()
            shouldReturnTest(999, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
            lines += nextLine()
            shouldReturnTest(999, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
            lines += nextLine()
            shouldReturnTest(999, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
        }

        run.tests.map { it.firstFrame() } shouldBe lines.map { "$SPEC_FILE:$it" }
    }

    "падение в контейнере, 0–8 входов: первый кадр стека — файл спеки и строка вызова" {
        val lines = mutableListOf<Int>()

        val run = runBehaviorSpec {
            Given("g") {
                lines += nextLine()
                shouldReturnTest(999) { 0 }
                lines += nextLine()
                shouldReturnTest(999, 1) { a -> a }
                lines += nextLine()
                shouldReturnTest(999, 1, 1) { a, b -> a + b }
                lines += nextLine()
                shouldReturnTest(999, 1, 1, 1) { a, b, c -> a + b + c }
                lines += nextLine()
                shouldReturnTest(999, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
                lines += nextLine()
                shouldReturnTest(999, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
                lines += nextLine()
                shouldReturnTest(999, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
                lines += nextLine()
                shouldReturnTest(999, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
                lines += nextLine()
                shouldReturnTest(999, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
            }
        }

        run.childrenOf(listOf("g")).map { it.firstFrame() } shouldBe lines.map { "$SPEC_FILE:$it" }
    }

    "своя проверка и другое исключение в блоке: первой идёт настоящая строка в блоке" {
        val lines = mutableListOf<Int>()

        val run = runBehaviorSpec {
            shouldReturnTest(0, options = TestOptions("проверка в корне")) {
                lines += nextLine()
                1 shouldBe 2
                0
            }
            shouldReturnTest(0, options = TestOptions("исключение в корне")) {
                lines += nextLine()
                throw IllegalStateException("x")
            }
            Given("g") {
                shouldReturnTest(0, options = TestOptions("проверка в контейнере")) {
                    lines += nextLine()
                    1 shouldBe 2
                    0
                }
                shouldReturnTest(0, options = TestOptions("исключение в контейнере")) {
                    lines += nextLine()
                    throw IllegalStateException("x")
                }
            }
        }

        val tests = run.tests.filter { it.type == TestType.Test }
        tests.map { it.result.isErrorOrFailure } shouldBe List(4) { true }
        tests.map { it.firstFrame() } shouldBe lines.map { "$SPEC_FILE:$it" }
    }

    "явные типы и null в ожидании компилируются и работают" {
        val run = runStringSpec {
            shouldReturnTest<Int, String>(1, "a") { it.length }
            shouldReturnTest(null, "b") { _ -> null }
            shouldReturnTest<Int?, String>(null, "c") { null }
        }

        run.tests.map { it.name } shouldBe listOf("\"a\" -> 1", "\"b\" -> <null>", "\"c\" -> <null>")
        run.tests.map { it.result.isSuccess } shouldBe List(3) { true }
    }

    "контейнер: явные типы и null в ожидании компилируются и работают" {
        val run = runBehaviorSpec {
            Given("g") {
                shouldReturnTest<Int, String>(1, "a") { it.length }
                shouldReturnTest(null, "b") { _ -> null }
            }
        }

        run.childrenOf(listOf("g")).map { it.result.isSuccess } shouldBe listOf(true, true)
    }
})

private const val SPEC_FILE = "ShouldReturnTestExtSpec.kt"

private enum class Color { RED }

private data class User(val id: Int)

private class Repo

private data class Holder(val repo: Repo)

private class ThrowingToString {
    override fun toString(): String = error("x")
}

private class CyclicToString {
    override fun toString(): String = "$this"
}

private fun same(value: Int): Int = value

/** Номер строки, следующей за вызовом. Кадры: сама функция, её синтетический `access$`, вызвавшая лямбда. */
private fun nextLine(): Int = Throwable().stackTrace[2].lineNumber + 1

private fun RanTest.firstFrame(): String? =
    result.errorOrNull?.stackTrace?.first()?.let { "${it.fileName}:${it.lineNumber}" }

private val DEFAULT_NAMES = listOf(
    "expected <empty string>",
    "\"1\" -> \"1\"",
    "(\"1\", \"2\") -> \"12\"",
    "(\"1\", \"2\", \"3\") -> \"123\"",
    "(\"1\", \"2\", \"3\", \"4\") -> \"1234\"",
    "(\"1\", \"2\", \"3\", \"4\", \"5\") -> \"12345\"",
    "(\"1\", \"2\", \"3\", \"4\", \"5\", \"6\") -> \"123456\"",
    "(\"1\", \"2\", \"3\", \"4\", \"5\", \"6\", \"7\") -> \"1234567\"",
    "(\"1\", \"2\", \"3\", \"4\", \"5\", \"6\", \"7\", \"8\") -> \"12345678\"",
)

/** Все формы с 0–8 входами: конкатенация в ожидании заодно проверяет порядок входов. */
private suspend fun ContainerScope.allForms(options: (Int) -> TestOptions = { TestOptions() }) {
    shouldReturnTest("", options = options(0)) { "" }
    shouldReturnTest("1", "1", options = options(1)) { a -> a }
    shouldReturnTest("12", "1", "2", options = options(2)) { a, b -> a + b }
    shouldReturnTest("123", "1", "2", "3", options = options(3)) { a, b, c -> a + b + c }
    shouldReturnTest("1234", "1", "2", "3", "4", options = options(4)) { a, b, c, d -> a + b + c + d }
    shouldReturnTest("12345", "1", "2", "3", "4", "5", options = options(5)) { a, b, c, d, e -> a + b + c + d + e }
    shouldReturnTest("123456", "1", "2", "3", "4", "5", "6", options = options(6)) { a, b, c, d, e, f ->
        a + b + c + d + e + f
    }
    shouldReturnTest("1234567", "1", "2", "3", "4", "5", "6", "7", options = options(7)) { a, b, c, d, e, f, g ->
        a + b + c + d + e + f + g
    }
    shouldReturnTest("12345678", "1", "2", "3", "4", "5", "6", "7", "8", options = options(8)) { a, b, c, d, e, f, g, h ->
        a + b + c + d + e + f + g + h
    }
}
