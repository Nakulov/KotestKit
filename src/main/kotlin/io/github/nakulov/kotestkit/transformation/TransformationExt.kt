package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.INSIDE_TEST_MESSAGE
import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.registerLeaf
import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe

inline fun <reified Result> AbstractSpec.transformationTest(
    expectedResult: Result,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.() -> Result,
) {
    include(transformationTestFactory(expectedResult, Result::class, options, transformBlock))
}

inline fun <reified Result, Input> AbstractSpec.transformationTest(
    expectedResult: Result,
    input: Input,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input) -> Result,
) {
    include(transformationTestFactory(expectedResult, Result::class, input, options, transformBlock))
}

inline fun <reified Result, Input1, Input2> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2) -> Result,
) {
    include(transformationTestFactory(expectedResult, Result::class, input1, input2, options, transformBlock))
}

inline fun <reified Result, Input1, Input2, Input3> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
) {
    include(transformationTestFactory(expectedResult, Result::class, input1, input2, input3, options, transformBlock))
}

inline fun <reified Result, Input1, Input2, Input3, Input4> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
) {
    include(
        transformationTestFactory(expectedResult, Result::class, input1, input2, input3, input4, options, transformBlock)
    )
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
) {
    include(
        transformationTestFactory(
            expectedResult,
            Result::class,
            input1,
            input2,
            input3,
            input4,
            input5,
            options,
            transformBlock
        )
    )
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
) {
    include(
        transformationTestFactory(
            expectedResult,
            Result::class,
            input1,
            input2,
            input3,
            input4,
            input5,
            input6,
            options,
            transformBlock
        )
    )
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
) {
    include(
        transformationTestFactory(
            expectedResult,
            Result::class,
            input1,
            input2,
            input3,
            input4,
            input5,
            input6,
            input7,
            options,
            transformBlock
        )
    )
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> AbstractSpec.transformationTest(
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
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
) {
    include(
        transformationTestFactory(
            expectedResult,
            Result::class,
            input1,
            input2,
            input3,
            input4,
            input5,
            input6,
            input7,
            input8,
            options,
            transformBlock
        )
    )
}

suspend inline fun <reified Result> ContainerScope.transformationTest(
    expectedResult: Result,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.() -> Result,
) {
    registerLeaf("transformation to ${Result::class.simpleName}", options) {
        transformBlock(this) shouldBe expectedResult
    }
}

suspend inline fun <reified Result, Input> ContainerScope.transformationTest(
    expectedResult: Result,
    input: Input,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input) -> Result,
) {
    registerLeaf("transformation ${input.className} to ${Result::class.simpleName}", options) {
        transformBlock(this, input) shouldBe expectedResult
    }
}

suspend inline fun <reified Result, Input1, Input2> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2) -> Result,
) {
    val inputs = listOf(input1.className, input2.className).joinToString(" and ")
    registerLeaf("transformation $inputs to ${Result::class.simpleName}", options) {
        transformBlock(this, input1, input2) shouldBe expectedResult
    }
}

suspend inline fun <reified Result, Input1, Input2, Input3> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
) {
    val inputs = listOf(input1.className, input2.className, input3.className).joinToString(" and ")
    registerLeaf("transformation $inputs to ${Result::class.simpleName}", options) {
        transformBlock(this, input1, input2, input3) shouldBe expectedResult
    }
}

suspend inline fun <reified Result, Input1, Input2, Input3, Input4> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
) {
    val inputs = listOf(input1.className, input2.className, input3.className, input4.className).joinToString(" and ")
    registerLeaf("transformation $inputs to ${Result::class.simpleName}", options) {
        transformBlock(this, input1, input2, input3, input4) shouldBe expectedResult
    }
}

suspend inline fun <reified Result, Input1, Input2, Input3, Input4, Input5> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
) {
    val inputs = listOf(input1.className, input2.className, input3.className, input4.className, input5.className).joinToString(" and ")
    registerLeaf("transformation $inputs to ${Result::class.simpleName}", options) {
        transformBlock(this, input1, input2, input3, input4, input5) shouldBe expectedResult
    }
}

suspend inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
) {
    val inputs = listOf(
        input1.className,
        input2.className,
        input3.className,
        input4.className,
        input5.className,
        input6.className,
    ).joinToString(" and ")
    registerLeaf("transformation $inputs to ${Result::class.simpleName}", options) {
        transformBlock(this, input1, input2, input3, input4, input5, input6) shouldBe expectedResult
    }
}

suspend inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> ContainerScope.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
) {
    val inputs = listOf(
        input1.className,
        input2.className,
        input3.className,
        input4.className,
        input5.className,
        input6.className,
        input7.className,
    ).joinToString(" and ")
    registerLeaf("transformation $inputs to ${Result::class.simpleName}", options) {
        transformBlock(this, input1, input2, input3, input4, input5, input6, input7) shouldBe expectedResult
    }
}

suspend inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> ContainerScope.transformationTest(
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
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
) {
    val inputs = listOf(
        input1.className,
        input2.className,
        input3.className,
        input4.className,
        input5.className,
        input6.className,
        input7.className,
        input8.className,
    ).joinToString(" and ")
    registerLeaf("transformation $inputs to ${Result::class.simpleName}", options) {
        transformBlock(this, input1, input2, input3, input4, input5, input6, input7, input8) shouldBe expectedResult
    }
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
