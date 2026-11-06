@file:OptIn(io.kotest.common.KotestInternal::class)

package io.github.nakulov.kotestkit.testing

import io.kotest.core.spec.SpecRef
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
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

internal class SpecRun(
    /** Все узлы, включая Given и When, в порядке завершения. */
    val tests: List<RanTest>,
    /** Исключение в теле спеки, иначе `null`. */
    val specError: Throwable?,
)

internal class RanTest(
    /** Имена от корня до самого теста, например `["g", "w", "t"]`. */
    val path: List<String>,
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
        tests += RanTest(testCase.path(), testCase.config, result)
    }

    override suspend fun testIgnored(testCase: TestCase, reason: String?) {
        tests += RanTest(testCase.path(), testCase.config, TestResult.Ignored(reason))
    }

    override suspend fun specFinished(ref: SpecRef, result: TestResult) {
        specError = result.errorOrNull
    }
}

private fun TestCase.path(): List<String> =
    generateSequence(this) { it.parent }.map { it.name.name }.toList().asReversed()
