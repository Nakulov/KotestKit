package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.callSite
import io.github.nakulov.kotestkit.displayValue
import io.kotest.core.factory.TestFactory
import io.kotest.core.spec.style.stringSpec
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

internal fun <Result> transformationTestFactory(
    expectedResult: Result,
    options: TestOptions,
    transformBlock: suspend TestScope.() -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult)
        testName.config(this, options.config) {
            checkResult(transformBlock(this), expectedResult, site)
        }
    }
}

internal fun <Result, Input> transformationTestFactory(
    expectedResult: Result,
    input: Input,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input) -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult, input)
        testName.config(this, options.config) {
            checkResult(transformBlock(this, input), expectedResult, site)
        }
    }
}

internal fun <Result, Input1, Input2> transformationTestFactory(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2) -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult, input1, input2)
        testName.config(this, options.config) {
            checkResult(transformBlock(this, input1, input2), expectedResult, site)
        }
    }
}

internal fun <Result, Input1, Input2, Input3> transformationTestFactory(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult, input1, input2, input3)
        testName.config(this, options.config) {
            checkResult(transformBlock(this, input1, input2, input3), expectedResult, site)
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4> transformationTestFactory(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult, input1, input2, input3, input4)
        testName.config(this, options.config) {
            checkResult(transformBlock(this, input1, input2, input3, input4), expectedResult, site)
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4, Input5> transformationTestFactory(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult, input1, input2, input3, input4, input5)
        testName.config(this, options.config) {
            checkResult(transformBlock(this, input1, input2, input3, input4, input5), expectedResult, site)
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6> transformationTestFactory(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult, input1, input2, input3, input4, input5, input6)
        testName.config(this, options.config) {
            checkResult(transformBlock(this, input1, input2, input3, input4, input5, input6), expectedResult, site)
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> transformationTestFactory(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult, input1, input2, input3, input4, input5, input6, input7)
        testName.config(this, options.config) {
            checkResult(transformBlock(this, input1, input2, input3, input4, input5, input6, input7), expectedResult, site)
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> transformationTestFactory(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    input8: Input8,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
): TestFactory {
    val site = callSite()
    return stringSpec {
        val testName = options.name ?: transformationName(expectedResult, input1, input2, input3, input4, input5, input6, input7, input8)
        testName.config(this, options.config) {
            checkResult(transformBlock(this, input1, input2, input3, input4, input5, input6, input7, input8), expectedResult, site)
        }
    }
}
