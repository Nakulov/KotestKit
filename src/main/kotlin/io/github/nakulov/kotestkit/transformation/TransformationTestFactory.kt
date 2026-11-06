package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.callSite
import io.github.nakulov.kotestkit.displayValue
import io.github.nakulov.kotestkit.leafTest
import io.kotest.core.spec.TestDefinition
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe

/** Имя по умолчанию: `expected 3`, `"ab" -> 99`, `("a", 1, <null>) -> 0`. */
internal fun transformationName(expectedResult: Any?, vararg inputs: Any?): String {
    val expected = displayValue(expectedResult)
    return when (inputs.size) {
        0 -> "expected $expected"
        1 -> "${displayValue(inputs.single())} -> $expected"
        else -> inputs.joinToString(", ", "(", ")") { displayValue(it) } + " -> $expected"
    }
}

/** Ставит место вызова первым в стек только при несовпадении: падения блока автора и так ведут к своей строке. */
internal fun <Result> checkResult(actual: Result, expectedResult: Result, site: StackTraceElement) {
    try {
        actual shouldBe expectedResult
    } catch (e: AssertionError) {
        e.stackTrace = arrayOf(site) + e.stackTrace
        throw e
    }
}

/** Тест на возвращаемое значение. [inputs] нужны только для имени: [test] уже держит их в замыкании. */
internal fun <Result> returnTest(
    expectedResult: Result,
    options: TestOptions,
    vararg inputs: Any?,
    test: suspend TestScope.() -> Result,
): TestDefinition {
    val site = callSite()
    return leafTest(options, { transformationName(expectedResult, *inputs) }) {
        checkResult(test(), expectedResult, site)
    }
}
