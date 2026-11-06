package io.github.nakulov.kotestkit

import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.TestDefinition
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe

/** Имя по умолчанию: `expected 3`, `"ab" -> 99`, `("a", 1, <null>) -> 0`. */
private fun returnTestName(expected: Any?, vararg inputs: Any?): String {
    val shown = displayValue(expected)
    return when (inputs.size) {
        0 -> "expected $shown"
        1 -> "${displayValue(inputs.single())} -> $shown"
        else -> inputs.joinToString(", ", "(", ")") { displayValue(it) } + " -> $shown"
    }
}

/** Ставит место вызова первым в стек только при несовпадении: падения блока автора и так ведут к своей строке. */
private fun <Output> checkResult(actual: Output, expected: Output, site: StackTraceElement) {
    try {
        actual shouldBe expected
    } catch (e: AssertionError) {
        e.stackTrace = arrayOf(site) + e.stackTrace
        throw e
    }
}

/** Тест на возвращаемое значение. [inputs] нужны только для имени: [test] уже держит их в замыкании. */
private fun <Output> returnTest(
    expected: Output,
    options: TestOptions,
    vararg inputs: Any?,
    test: suspend TestScope.() -> Output,
): TestDefinition {
    val site = callSite()
    return leafTest(options, { returnTestName(expected, *inputs) }) {
        checkResult(test(), expected, site)
    }
}

fun <Output> AbstractSpec.shouldReturnTest(
    expected: Output,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.() -> Output,
) {
    add(returnTest(expected, options) { block() })
}

fun <Output, Input> AbstractSpec.shouldReturnTest(
    expected: Output,
    input: Input,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input) -> Output,
) {
    add(returnTest(expected, options, input) { block(input) })
}

fun <Output, Input1, Input2> AbstractSpec.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2) -> Output,
) {
    add(returnTest(expected, options, input1, input2) { block(input1, input2) })
}

fun <Output, Input1, Input2, Input3> AbstractSpec.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3) -> Output,
) {
    add(returnTest(expected, options, input1, input2, input3) { block(input1, input2, input3) })
}

fun <Output, Input1, Input2, Input3, Input4> AbstractSpec.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4) -> Output,
) {
    add(returnTest(expected, options, input1, input2, input3, input4) { block(input1, input2, input3, input4) })
}

fun <Output, Input1, Input2, Input3, Input4, Input5> AbstractSpec.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Output,
) {
    add(returnTest(expected, options, input1, input2, input3, input4, input5) { block(input1, input2, input3, input4, input5) })
}

fun <Output, Input1, Input2, Input3, Input4, Input5, Input6> AbstractSpec.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Output,
) {
    add(returnTest(expected, options, input1, input2, input3, input4, input5, input6) { block(input1, input2, input3, input4, input5, input6) })
}

fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7> AbstractSpec.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Output,
) {
    add(returnTest(expected, options, input1, input2, input3, input4, input5, input6, input7) { block(input1, input2, input3, input4, input5, input6, input7) })
}

fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> AbstractSpec.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    input8: Input8,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Output,
) {
    add(returnTest(expected, options, input1, input2, input3, input4, input5, input6, input7, input8) { block(input1, input2, input3, input4, input5, input6, input7, input8) })
}

suspend fun <Output> ContainerScope.shouldReturnTest(
    expected: Output,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.() -> Output,
) {
    registerTest(returnTest(expected, options) { block() })
}

suspend fun <Output, Input> ContainerScope.shouldReturnTest(
    expected: Output,
    input: Input,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input) -> Output,
) {
    registerTest(returnTest(expected, options, input) { block(input) })
}

suspend fun <Output, Input1, Input2> ContainerScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2) -> Output,
) {
    registerTest(returnTest(expected, options, input1, input2) { block(input1, input2) })
}

suspend fun <Output, Input1, Input2, Input3> ContainerScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3) -> Output,
) {
    registerTest(returnTest(expected, options, input1, input2, input3) { block(input1, input2, input3) })
}

suspend fun <Output, Input1, Input2, Input3, Input4> ContainerScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4) -> Output,
) {
    registerTest(returnTest(expected, options, input1, input2, input3, input4) { block(input1, input2, input3, input4) })
}

suspend fun <Output, Input1, Input2, Input3, Input4, Input5> ContainerScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Output,
) {
    registerTest(returnTest(expected, options, input1, input2, input3, input4, input5) { block(input1, input2, input3, input4, input5) })
}

suspend fun <Output, Input1, Input2, Input3, Input4, Input5, Input6> ContainerScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Output,
) {
    registerTest(returnTest(expected, options, input1, input2, input3, input4, input5, input6) { block(input1, input2, input3, input4, input5, input6) })
}

suspend fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7> ContainerScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Output,
) {
    registerTest(returnTest(expected, options, input1, input2, input3, input4, input5, input6, input7) { block(input1, input2, input3, input4, input5, input6, input7) })
}

suspend fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> ContainerScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    input8: Input8,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Output,
) {
    registerTest(returnTest(expected, options, input1, input2, input3, input4, input5, input6, input7, input8) { block(input1, input2, input3, input4, input5, input6, input7, input8) })
}

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output> TestScope.shouldReturnTest(
    expected: Output,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.() -> Output,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output, Input> TestScope.shouldReturnTest(
    expected: Output,
    input: Input,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input) -> Output,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output, Input1, Input2> TestScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2) -> Output,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output, Input1, Input2, Input3> TestScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3) -> Output,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output, Input1, Input2, Input3, Input4> TestScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4) -> Output,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output, Input1, Input2, Input3, Input4, Input5> TestScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Output,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output, Input1, Input2, Input3, Input4, Input5, Input6> TestScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Output,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7> TestScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Output,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Output, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> TestScope.shouldReturnTest(
    expected: Output,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    input8: Input8,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Output,
): Unit = error("unreachable")
