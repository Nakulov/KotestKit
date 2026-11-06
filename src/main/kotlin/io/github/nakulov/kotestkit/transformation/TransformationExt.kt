package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.TestOptions
import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.style.scopes.BehaviorSpecContextContainerScope
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe

inline fun <reified Result> AbstractSpec.transformationTest(
    expectedResult: Result,
    options: TestOptions = TestOptions(),
    noinline transformBlock: suspend TestScope.() -> Result,
) {
    include(transformationTestFactory(expectedResult, Result::class, options, transformBlock))
}

suspend inline fun <reified Result> BehaviorSpecContextContainerScope.transformationTest(
    expectedResult: Result,
    noinline transformBlock: suspend TestScope.() -> Result,
) {
    transformBlock() shouldBe expectedResult
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
