package io.github.nakulov.kotestkit.transformation

import io.github.nakulov.kotestkit.TestOptions
import io.kotest.core.factory.TestFactory
import io.kotest.core.spec.style.stringSpec
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe
import kotlin.reflect.KClass

@PublishedApi
internal fun <Result> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
    options: TestOptions,
    transformBlock: suspend TestScope.() -> Result,
): TestFactory {
    return stringSpec {
        val testName = options.name ?: "transformation to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this) shouldBe expectedResult
        }
    }
}

@PublishedApi
internal fun <Result, Input> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
    input: Input,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input) -> Result,
): TestFactory {
    return stringSpec {
        val testName = options.name ?: "transformation ${input.className} to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this, input) shouldBe expectedResult
        }
    }
}

@PublishedApi
internal fun <Result, Input1, Input2> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
    input1: Input1,
    input2: Input2,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(input1.className, input2.className)
            .joinToString(" and ")
        val testName = options.name ?: "transformation $inputs to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this, input1, input2) shouldBe expectedResult
        }
    }
}

@PublishedApi
internal fun <Result, Input1, Input2, Input3> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(input1.className, input2.className, input3.className)
            .joinToString(" and ")
        val testName = options.name ?: "transformation $inputs to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this, input1, input2, input3) shouldBe expectedResult
        }
    }
}

@PublishedApi
internal fun <Result, Input1, Input2, Input3, Input4> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(input1.className, input2.className, input3.className, input4.className)
            .joinToString(" and ")
        val testName = options.name ?: "transformation $inputs to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this, input1, input2, input3, input4) shouldBe expectedResult
        }
    }
}

@PublishedApi
internal fun <Result, Input1, Input2, Input3, Input4, Input5> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(
            input1.className,
            input2.className,
            input3.className,
            input4.className,
            input5.className
        ).joinToString(" and ")
        val testName = options.name ?: "transformation $inputs to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this, input1, input2, input3, input4, input5) shouldBe expectedResult
        }
    }
}

@PublishedApi
internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
    input1: Input1,
    input2: Input2,
    input3: Input3,
    input4: Input4,
    input5: Input5,
    input6: Input6,
    options: TestOptions,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(
            input1.className,
            input2.className,
            input3.className,
            input4.className,
            input5.className,
            input6.className
        ).joinToString(" and ")
        val testName = options.name ?: "transformation $inputs to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this, input1, input2, input3, input4, input5, input6) shouldBe expectedResult
        }
    }
}

@PublishedApi
internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
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
    return stringSpec {
        val inputs = listOf(
            input1.className,
            input2.className,
            input3.className,
            input4.className,
            input5.className,
            input6.className,
            input7.className
        ).joinToString(" and ")
        val testName = options.name ?: "transformation $inputs to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this, input1, input2, input3, input4, input5, input6, input7) shouldBe expectedResult
        }
    }
}

@PublishedApi
internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> transformationTestFactory(
    expectedResult: Result,
    resultType: KClass<*>,
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
    return stringSpec {
        val inputs = listOf(
            input1.className,
            input2.className,
            input3.className,
            input4.className,
            input5.className,
            input6.className,
            input7.className,
            input8.className
        ).joinToString(" and ")
        val testName = options.name ?: "transformation $inputs to ${resultType.simpleName}"
        testName.config(this, options.config) {
            transformBlock(this, input1, input2, input3, input4, input5, input6, input7, input8) shouldBe expectedResult
        }
    }
}
