package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.case
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
import io.github.nakulov.kotestkit.withData
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
            "expected 3",
            "1 -> 3",
            "0 входов",
            "1 вход",
        )
        run.tests.map { it.result.isSuccess } shouldBe List(4) { true }
    }

    "корень, 0–8 входов: имя по умолчанию показывает значения, порядок входов сохранён" {
        val run = runStringSpec {
            transformationTest(0) { 0 }
            transformationTest(1, "a") { 1 }
            transformationTest(2, "a", 1) { _, _ -> 2 }
            transformationTest(3, "a", 1, null) { _, _, _ -> 3 }
            transformationTest(4, 1, 2, 3, 4) { _, _, _, _ -> 4 }
            transformationTest(5, 1, 2, 3, 4, 5) { _, _, _, _, _ -> 5 }
            transformationTest(6, 1, 2, 3, 4, 5, 6) { _, _, _, _, _, _ -> 6 }
            transformationTest(7, 1, 2, 3, 4, 5, 6, 7) { _, _, _, _, _, _, _ -> 7 }
            transformationTest(8, 1, 2, 3, 4, 5, 6, 7, 8) { _, _, _, _, _, _, _, _ -> 8 }
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
            transformationTest(0, options = TestOptions(config = TestConfig(blockingTest = true))) { 0 }
        }

        val test = run.tests.single()
        test.name shouldBe "expected 0"
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

    "withData: верное ожидание создаёт успешный конечный тест" {
        val run = runBehaviorSpec {
            withData(nameFn = { "c" }, case(1)) { _ ->
                transformationTest(3) { 3 }
            }
        }

        val test = run.childrenOf(listOf("c")).single()
        test.name shouldBe "expected 3"
        test.result.isSuccess shouldBe true
    }

    "withData: неверное ожидание роняет конечный тест, а не контейнер" {
        val run = runBehaviorSpec {
            withData(nameFn = { "c" }, case(1)) { _ ->
                transformationTest(999) { 3 }
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
                transformationTest(0, "a", 1, null) { _, _, _ -> 0 }
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
        }

        run.childrenOf(listOf("g")).map { it.result.isFailure } shouldBe List(9) { true }
    }

    "контейнер: падение проверки не отменяет следующую" {
        val run = runBehaviorSpec {
            Given("g") {
                transformationTest(999, options = TestOptions("упавшая")) { 0 }
                transformationTest(0, options = TestOptions("следующая")) { 0 }
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
                transformationTest(999, options = TestOptions(config = TestConfig(enabled = false))) { 0 }
            }
        }

        val test = run.childrenOf(listOf("g")).single()
        test.config?.enabled shouldBe false
        test.result.isIgnored shouldBe true
    }

    "контейнер: blockingTest доходит до Kotest" {
        val run = runBehaviorSpec {
            Given("g") {
                transformationTest(0, options = TestOptions(config = TestConfig(blockingTest = true))) { 0 }
            }
        }

        run.childrenOf(listOf("g")).single().config?.blockingTest shouldBe true
    }

    "контейнер любого стиля: вызов создаёт один успешный конечный тест" {
        val runs = listOf(
            runFunSpec { context("c") { transformationTest(0) { 0 } } },
            runDescribeSpec { describe("c") { transformationTest(0) { 0 } } },
            runFreeSpec { "c" - { transformationTest(0) { 0 } } },
            runWordSpec { "c" should { transformationTest(0) { 0 } } },
            runShouldSpec { context("c") { transformationTest(0) { 0 } } },
            runExpectSpec { context("c") { transformationTest(0) { 0 } } },
            runFeatureSpec { feature("c") { transformationTest(0) { 0 } } },
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
            transformationTest(1, "a") { it.length }
            transformationTest(1, "b") { it.length }
            Given("g") {
                transformationTest(1, "a") { it.length }
                transformationTest(2, "ab") { it.length }
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
            transformationTest(1, "a") { it.length }
            transformationTest(1, "a") { it.length }
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
            cases.forEach { (value, _) -> transformationTest(0, value) { _ -> 0 } }
        }

        lambda::class.java.name shouldContain "\$\$Lambda"
        run.tests.map { it.name } shouldBe cases.map { (_, shown) -> "$shown -> 0" }
    }

    "значение длиннее 40 символов обрезается, кавычки считаются" {
        val run = runStringSpec {
            transformationTest(0, "a".repeat(38)) { _ -> 0 }
            transformationTest(0, "a".repeat(39)) { _ -> 0 }
            transformationTest("b".repeat(100)) { "b".repeat(100) }
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
            transformationTest(0, ThrowingToString()) { _ -> 0 }
            transformationTest(0, CyclicToString()) { _ -> 0 }
            transformationTest(0, throwing) { _ -> 0 }
            transformationTest(0, "следующий") { _ -> 0 }
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
            transformationTest(999) { 0 }
            lines += nextLine()
            transformationTest(999, 1) { a -> a }
            lines += nextLine()
            transformationTest(999, 1, 1) { a, b -> a + b }
            lines += nextLine()
            transformationTest(999, 1, 1, 1) { a, b, c -> a + b + c }
            lines += nextLine()
            transformationTest(999, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
            lines += nextLine()
            transformationTest(999, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
            lines += nextLine()
            transformationTest(999, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
            lines += nextLine()
            transformationTest(999, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
            lines += nextLine()
            transformationTest(999, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
        }

        run.tests.map { it.firstFrame() } shouldBe lines.map { "$SPEC_FILE:$it" }
    }

    "падение в контейнере, 0–8 входов: первый кадр стека — файл спеки и строка вызова" {
        val lines = mutableListOf<Int>()

        val run = runBehaviorSpec {
            Given("g") {
                lines += nextLine()
                transformationTest(999) { 0 }
                lines += nextLine()
                transformationTest(999, 1) { a -> a }
                lines += nextLine()
                transformationTest(999, 1, 1) { a, b -> a + b }
                lines += nextLine()
                transformationTest(999, 1, 1, 1) { a, b, c -> a + b + c }
                lines += nextLine()
                transformationTest(999, 1, 1, 1, 1) { a, b, c, d -> a + b + c + d }
                lines += nextLine()
                transformationTest(999, 1, 1, 1, 1, 1) { a, b, c, d, e -> a + b + c + d + e }
                lines += nextLine()
                transformationTest(999, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f -> a + b + c + d + e + f }
                lines += nextLine()
                transformationTest(999, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g -> a + b + c + d + e + f + g }
                lines += nextLine()
                transformationTest(999, 1, 1, 1, 1, 1, 1, 1, 1) { a, b, c, d, e, f, g, h -> a + b + c + d + e + f + g + h }
            }
        }

        run.childrenOf(listOf("g")).map { it.firstFrame() } shouldBe lines.map { "$SPEC_FILE:$it" }
    }

    "своя проверка и другое исключение в блоке: первой идёт настоящая строка в блоке" {
        val lines = mutableListOf<Int>()

        val run = runBehaviorSpec {
            transformationTest(0, options = TestOptions("проверка в корне")) {
                lines += nextLine()
                1 shouldBe 2
                0
            }
            transformationTest(0, options = TestOptions("исключение в корне")) {
                lines += nextLine()
                throw IllegalStateException("x")
            }
            Given("g") {
                transformationTest(0, options = TestOptions("проверка в контейнере")) {
                    lines += nextLine()
                    1 shouldBe 2
                    0
                }
                transformationTest(0, options = TestOptions("исключение в контейнере")) {
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
            transformationTest<Int, String>(1, "a") { it.length }
            transformationTest(null, "b") { _ -> null }
            transformationTest<Int?, String>(null, "c") { null }
        }

        run.tests.map { it.name } shouldBe listOf("\"a\" -> 1", "\"b\" -> <null>", "\"c\" -> <null>")
        run.tests.map { it.result.isSuccess } shouldBe List(3) { true }
    }

    "контейнер: явные типы и null в ожидании компилируются и работают" {
        val run = runBehaviorSpec {
            Given("g") {
                transformationTest<Int, String>(1, "a") { it.length }
                transformationTest(null, "b") { _ -> null }
            }
        }

        run.childrenOf(listOf("g")).map { it.result.isSuccess } shouldBe listOf(true, true)
    }
})

private const val SPEC_FILE = "TransformationExtSpec.kt"

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
    transformationTest("", options = options(0)) { "" }
    transformationTest("1", "1", options = options(1)) { a -> a }
    transformationTest("12", "1", "2", options = options(2)) { a, b -> a + b }
    transformationTest("123", "1", "2", "3", options = options(3)) { a, b, c -> a + b + c }
    transformationTest("1234", "1", "2", "3", "4", options = options(4)) { a, b, c, d -> a + b + c + d }
    transformationTest("12345", "1", "2", "3", "4", "5", options = options(5)) { a, b, c, d, e -> a + b + c + d + e }
    transformationTest("123456", "1", "2", "3", "4", "5", "6", options = options(6)) { a, b, c, d, e, f ->
        a + b + c + d + e + f
    }
    transformationTest("1234567", "1", "2", "3", "4", "5", "6", "7", options = options(7)) { a, b, c, d, e, f, g ->
        a + b + c + d + e + f + g
    }
    transformationTest("12345678", "1", "2", "3", "4", "5", "6", "7", "8", options = options(8)) { a, b, c, d, e, f, g, h ->
        a + b + c + d + e + f + g + h
    }
}
