package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.INSIDE_TEST_MESSAGE
import io.github.nakulov.kotestkit.TestOptions
import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.TestScope

fun <Result> AbstractSpec.transformationTest(
    expectedResult: Result,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.() -> Result,
) {
    add(returnTest(expectedResult, options) { transformBlock() })
}

fun <Result, Input> AbstractSpec.transformationTest(
    expectedResult: Result,
    input: Input,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input) -> Result,
) {
    add(returnTest(expectedResult, options, input) { transformBlock(input) })
}

fun <Result, Input1, Input2> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2) -> Result,
) {
    add(returnTest(expectedResult, options, input1, input2) { transformBlock(input1, input2) })
}

fun <Result, Input1, Input2, Input3> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
) {
    add(returnTest(expectedResult, options, input1, input2, input3) { transformBlock(input1, input2, input3) })
}

fun <Result, Input1, Input2, Input3, Input4> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
) {
    add(returnTest(expectedResult, options, input1, input2, input3, input4) { transformBlock(input1, input2, input3, input4) })
}

fun <Result, Input1, Input2, Input3, Input4, Input5> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
) {
    add(returnTest(expectedResult, options, input1, input2, input3, input4, input5) { transformBlock(input1, input2, input3, input4, input5) })
}

fun <Result, Input1, Input2, Input3, Input4, Input5, Input6> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
) {
    add(returnTest(expectedResult, options, input1, input2, input3, input4, input5, input6) { transformBlock(input1, input2, input3, input4, input5, input6) })
}

fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
) {
    add(returnTest(expectedResult, options, input1, input2, input3, input4, input5, input6, input7) { transformBlock(input1, input2, input3, input4, input5, input6, input7) })
}

fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    input8: Input8,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
) {
    add(returnTest(expectedResult, options, input1, input2, input3, input4, input5, input6, input7, input8) { transformBlock(input1, input2, input3, input4, input5, input6, input7, input8) })
}

suspend fun <Result> ContainerScope.transformationTest(
    expectedResult: Result,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.() -> Result,
) {
    registerTest(returnTest(expectedResult, options) { transformBlock() })
}

suspend fun <Result, Input> ContainerScope.transformationTest(
    expectedResult: Result,
    input: Input,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input) -> Result,
) {
    registerTest(returnTest(expectedResult, options, input) { transformBlock(input) })
}

suspend fun <Result, Input1, Input2> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2) -> Result,
) {
    registerTest(returnTest(expectedResult, options, input1, input2) { transformBlock(input1, input2) })
}

suspend fun <Result, Input1, Input2, Input3> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
) {
    registerTest(returnTest(expectedResult, options, input1, input2, input3) { transformBlock(input1, input2, input3) })
}

suspend fun <Result, Input1, Input2, Input3, Input4> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
) {
    registerTest(returnTest(expectedResult, options, input1, input2, input3, input4) { transformBlock(input1, input2, input3, input4) })
}

suspend fun <Result, Input1, Input2, Input3, Input4, Input5> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
) {
    registerTest(returnTest(expectedResult, options, input1, input2, input3, input4, input5) { transformBlock(input1, input2, input3, input4, input5) })
}

suspend fun <Result, Input1, Input2, Input3, Input4, Input5, Input6> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
) {
    registerTest(returnTest(expectedResult, options, input1, input2, input3, input4, input5, input6) { transformBlock(input1, input2, input3, input4, input5, input6) })
}

suspend fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
) {
    registerTest(returnTest(expectedResult, options, input1, input2, input3, input4, input5, input6, input7) { transformBlock(input1, input2, input3, input4, input5, input6, input7) })
}

suspend fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    input8: Input8,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
) {
    registerTest(returnTest(expectedResult, options, input1, input2, input3, input4, input5, input6, input7, input8) { transformBlock(input1, input2, input3, input4, input5, input6, input7, input8) })
}

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result> TestScope.transformationTest(
    expectedResult: Result,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.() -> Result,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result, Input> TestScope.transformationTest(
    expectedResult: Result,
    input: Input,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input) -> Result,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result, Input1, Input2> TestScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2) -> Result,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result, Input1, Input2, Input3> TestScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result, Input1, Input2, Input3, Input4> TestScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result, Input1, Input2, Input3, Input4, Input5> TestScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result, Input1, Input2, Input3, Input4, Input5, Input6> TestScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> TestScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> TestScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    input8: Input8,
    options: TestOptions = TestOptions(),
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
): Unit = error("unreachable")
