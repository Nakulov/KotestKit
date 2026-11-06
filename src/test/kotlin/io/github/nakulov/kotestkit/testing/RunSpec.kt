@file:OptIn(io.kotest.common.KotestInternal::class)

package io.github.nakulov.kotestkit.testing

import io.github.nakulov.kotestkit.case
import io.github.nakulov.kotestkit.withGivens
import io.kotest.core.spec.SpecRef
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestType
import io.kotest.core.test.config.TestConfig
import io.kotest.engine.TestEngineLauncher
import io.kotest.engine.listener.AbstractTestEngineListener
import io.kotest.engine.test.TestResult

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runStringSpec(body: StringSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : StringSpec(body) {} }, StringSpec::class))

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runBehaviorSpec(body: BehaviorSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : BehaviorSpec(body) {} }, BehaviorSpec::class))

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runFunSpec(body: FunSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : FunSpec(body) {} }, FunSpec::class))

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runDescribeSpec(body: DescribeSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : DescribeSpec(body) {} }, DescribeSpec::class))

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runFreeSpec(body: FreeSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : FreeSpec(body) {} }, FreeSpec::class))

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runWordSpec(body: WordSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : WordSpec(body) {} }, WordSpec::class))

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runShouldSpec(body: ShouldSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : ShouldSpec(body) {} }, ShouldSpec::class))

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runExpectSpec(body: ExpectSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : ExpectSpec(body) {} }, ExpectSpec::class))

/** Прогоняет спеку из [body] отдельным запуском движка Kotest и возвращает отчёт. */
internal suspend fun runFeatureSpec(body: FeatureSpec.() -> Unit): SpecRun =
    runSpec(SpecRef.Function({ object : FeatureSpec(body) {} }, FeatureSpec::class))

/** Пути контейнеров, которые создаёт [inEachContainer]. */
internal val BEHAVIOR_CONTAINERS = listOf(
    listOf("withGivens"),
    listOf("Context"),
    listOf("Given"),
    listOf("g", "When"),
)

/** Выполняет [body] в каждом контейнере `BehaviorSpec`. */
internal fun BehaviorSpec.inEachContainer(body: suspend ContainerScope.() -> Unit) {
    withGivens(nameFn = { "withGivens" }, case(1)) { _ -> body() }
    Context("Context") { body() }
    Given("Given") { body() }
    Given("g") {
        When("When") { body() }
    }
}

internal class SpecRun(
    /** Все узлы, включая Given и When, в порядке завершения. */
    val tests: List<RanTest>,
    /** Исключение в теле спеки, иначе `null`. */
    val specError: Throwable?,
) {
    /** Прямые потомки контейнера с путём [path]. */
    fun childrenOf(path: List<String>): List<RanTest> = tests.filter { it.path.dropLast(1) == path }
}

internal class RanTest(
    /** Имена от корня до самого теста, например `["g", "w", "t"]`. */
    val path: List<String>,
    /** Префикс имени из стиля спеки, например `Given: `, иначе `null`. */
    val prefix: String?,
    /** Контейнер или конечный тест. */
    val type: TestType,
    /** Настройки, как их увидел Kotest. */
    val config: TestConfig?,
    val result: TestResult,
) {
    val name: String get() = path.last()
}

private suspend fun runSpec(spec: SpecRef): SpecRun {
    val listener = CollectingListener()
    val engine = TestEngineLauncher().withListener(listener).withSpecRefs(spec).execute()
    // Поломка движка означает, что отчёту нельзя верить.
    engine.errors.firstOrNull()?.let { throw it }
    return SpecRun(listener.tests, listener.specError)
}

private class CollectingListener : AbstractTestEngineListener() {
    val tests = mutableListOf<RanTest>()
    var specError: Throwable? = null

    override suspend fun testFinished(testCase: TestCase, result: TestResult) {
        tests += testCase.toRanTest(result)
    }

    override suspend fun testIgnored(testCase: TestCase, reason: String?) {
        tests += testCase.toRanTest(TestResult.Ignored(reason))
    }

    override suspend fun specFinished(ref: SpecRef, result: TestResult) {
        specError = result.errorOrNull
    }
}

private fun TestCase.toRanTest(result: TestResult) = RanTest(path(), name.prefix, type, config, result)

private fun TestCase.path(): List<String> =
    generateSequence(this) { it.parent }.map { it.name.name }.toList().asReversed()
