package io.github.nakulov.kotestkit.transformation

import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.style.scopes.BehaviorSpecContextContainerScope
import io.kotest.core.test.TestScope

inline fun <reified Result> AbstractSpec.transformationTest(
    expectedResult: Result,
): TransformationTestDsl<Result> {
    return transformationTest(lazyOf(expectedResult))
}

inline fun <reified Result> BehaviorSpecContextContainerScope.transformationTest(
    expectedResult: Result,
): TransformationTestDslWithData<Result> {
    return transformationTest(lazyOf(expectedResult))
}

inline fun <reified Result, Input> AbstractSpec.transformationTest(
    expectedResult: Result,
    input: Input,
): TransformationTestDsl1<Result, Input> {
    return transformationTest(lazyOf(expectedResult), input)
}

inline fun <reified Result, Input1, Input2> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
): TransformationTestDsl2<Result, Input1, Input2> {
    return transformationTest(lazyOf(expectedResult), input1, input2)
}

inline fun <reified Result, Input1, Input2, Input3> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
): TransformationTestDsl3<Result, Input1, Input2, Input3> {
    return transformationTest(lazyOf(expectedResult), input1, input2, input3)
}

inline fun <reified Result, Input1, Input2, Input3, Input4> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4
): TransformationTestDsl4<Result, Input1, Input2, Input3, Input4> {
    return transformationTest(lazyOf(expectedResult), input1, input2, input3, input4)
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
): TransformationTestDsl5<Result, Input1, Input2, Input3, Input4, Input5> {
    return transformationTest(lazyOf(expectedResult), input1, input2, input3, input4, input5)
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
): TransformationTestDsl6<Result, Input1, Input2, Input3, Input4, Input5, Input6> {
    return transformationTest(lazyOf(expectedResult), input1, input2, input3, input4, input5, input6)
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
): TransformationTestDsl7<Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> {
    return transformationTest(lazyOf(expectedResult), input1, input2, input3, input4, input5, input6, input7)
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
): TransformationTestDsl8<Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> {
    return transformationTest(lazyOf(expectedResult), input1, input2, input3, input4, input5, input6, input7, input8)
}

inline fun <reified Result> AbstractSpec.transformationTest(
    expectedResult: Result,
    noinline transformBlock: suspend TestScope.() -> Result,
) {
    transformationTest(expectedResult).config(transformBlock = transformBlock)
}

suspend inline fun <reified Result> BehaviorSpecContextContainerScope.transformationTest(
    expectedResult: Result,
    noinline transformBlock: suspend TestScope.() -> Result,
) {
    transformationTest(expectedResult).config(transformBlock = transformBlock)
}


inline fun <reified Result, Input> AbstractSpec.transformationTest(
    expectedResult: Result,
    input: Input,
    noinline transformBlock: suspend TestScope.(Input) -> Result,
) {
    transformationTest(expectedResult, input).config(transformBlock = transformBlock)
}

inline fun <reified Result, Input1, Input2> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    noinline transformBlock: suspend TestScope.(Input1, Input2) -> Result,
) {
    transformationTest(expectedResult, input1, input2).config(transformBlock = transformBlock)
}

inline fun <reified Result, Input1, Input2, Input3> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
) {
    transformationTest(expectedResult, input1, input2, input3).config(transformBlock = transformBlock)
}

inline fun <reified Result, Input1, Input2, Input3, Input4> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
) {
    transformationTest(expectedResult, input1, input2, input3, input4).config(transformBlock = transformBlock)
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
) {
    transformationTest(expectedResult, input1, input2, input3, input4, input5).config(transformBlock = transformBlock)
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6> AbstractSpec.transformationTest(
    expectedResult: Result,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
) {
    transformationTest(expectedResult, input1, input2, input3, input4, input5, input6).config(
        transformBlock = transformBlock
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
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
) {
    transformationTest(
        expectedResult,
        input1,
        input2,
        input3,
        input4,
        input5,
        input6,
        input7
    ).config(transformBlock = transformBlock)
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
    noinline transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
) {
    transformationTest(expectedResult, input1, input2, input3, input4, input5, input6, input7, input8).config(
        transformBlock = transformBlock
    )
}

inline fun <reified Result> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
): TransformationTestDsl<Result> {
    return TransformationTestDsl(this, expectedResultLazy, Result::class)
}

inline fun <reified Result> BehaviorSpecContextContainerScope.transformationTest(
    expectedResultLazy: Lazy<Result>,
): TransformationTestDslWithData<Result> {
    return TransformationTestDslWithData(this, expectedResultLazy, Result::class)
}


inline fun <reified Result, Input> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
    input: Input,
): TransformationTestDsl1<Result, Input> {
    return TransformationTestDsl1(this, expectedResultLazy, Result::class, input)
}

inline fun <reified Result, Input1, Input2> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
    input1: Input1,
    input2: Input2,
): TransformationTestDsl2<Result, Input1, Input2> {
    return TransformationTestDsl2(this, expectedResultLazy, Result::class, input1, input2)
}

inline fun <reified Result, Input1, Input2, Input3> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
): TransformationTestDsl3<Result, Input1, Input2, Input3> {
    return TransformationTestDsl3(this, expectedResultLazy, Result::class, input1, input2, input3)
}

inline fun <reified Result, Input1, Input2, Input3, Input4> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
): TransformationTestDsl4<Result, Input1, Input2, Input3, Input4> {
    return TransformationTestDsl4(this, expectedResultLazy, Result::class, input1, input2, input3, input4)
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
): TransformationTestDsl5<Result, Input1, Input2, Input3, Input4, Input5> {
    return TransformationTestDsl5(this, expectedResultLazy, Result::class, input1, input2, input3, input4, input5)
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
): TransformationTestDsl6<Result, Input1, Input2, Input3, Input4, Input5, Input6> {
    return TransformationTestDsl6(
        this,
        expectedResultLazy,
        Result::class,
        input1,
        input2,
        input3,
        input4,
        input5,
        input6
    )
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
    input1: Input1,
    input2: Input2,
    input3: Input3, input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
): TransformationTestDsl7<Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> {
    return TransformationTestDsl7(
        this,
        expectedResultLazy,
        Result::class,
        input1,
        input2,
        input3,
        input4,
        input5,
        input6,
        input7
    )
}

inline fun <reified Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> AbstractSpec.transformationTest(
    expectedResultLazy: Lazy<Result>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    input7: Input7,
    input8: Input8,
): TransformationTestDsl8<Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> {
    return TransformationTestDsl8(
        this,
        expectedResultLazy,
        Result::class,
        input1,
        input2,
        input3,
        input4,
        input5,
        input6,
        input7,
        input8
    )
}