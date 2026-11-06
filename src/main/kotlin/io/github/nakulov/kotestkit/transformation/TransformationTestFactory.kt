package io.github.nakulov.kotestkit.transformation

import io.kotest.core.factory.TestFactory
import io.kotest.core.spec.style.stringSpec
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe

internal fun <Result> transformationTestFactory(
    testDsl: TransformationTestDsl<Result>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.() -> Result,
): TestFactory {
    return stringSpec {
        val testName = config.testName ?: "transformation to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(this) shouldBe result
        }
    }
}

internal fun <Result, Input> transformationTestFactory(
    testDsl: TransformationTestDsl1<Result, Input>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.(Input) -> Result,
): TestFactory {
    return stringSpec {
        val testName = config.testName
            ?: "transformation ${testDsl.input.className} to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(this, testDsl.input) shouldBe result
        }
    }
}

internal fun <Result, Input1, Input2> transformationTestFactory(
    testDsl: TransformationTestDsl2<Result, Input1, Input2>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.(Input1, Input2) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(testDsl.input1.className, testDsl.input2.className)
            .joinToString(" and ")
        val testName = config.testName ?: "transformation $inputs to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(this, testDsl.input1, testDsl.input2) shouldBe result
        }
    }
}

internal fun <Result, Input1, Input2, Input3> transformationTestFactory(
    testDsl: TransformationTestDsl3<Result, Input1, Input2, Input3>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(testDsl.input1.className, testDsl.input2.className, testDsl.input3.className)
            .joinToString(" and ")
        val testName = config.testName ?: "transformation $inputs to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(this, testDsl.input1, testDsl.input2, testDsl.input3) shouldBe result
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4> transformationTestFactory(
    testDsl: TransformationTestDsl4<Result, Input1, Input2, Input3, Input4>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(
            testDsl.input1.className,
            testDsl.input2.className,
            testDsl.input3.className,
            testDsl.input4.className
        )
            .joinToString(" and ")
        val testName = config.testName ?: "transformation $inputs to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(this, testDsl.input1, testDsl.input2, testDsl.input3, testDsl.input4) shouldBe result
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4, Input5> transformationTestFactory(
    testDsl: TransformationTestDsl5<Result, Input1, Input2, Input3, Input4, Input5>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(
            testDsl.input1.className,
            testDsl.input2.className,
            testDsl.input3.className,
            testDsl.input4.className,
            testDsl.input5.className
        ).joinToString(" and ")
        val testName = config.testName ?: "transformation $inputs to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(
                this,
                testDsl.input1,
                testDsl.input2,
                testDsl.input3,
                testDsl.input4,
                testDsl.input5
            ) shouldBe result
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6> transformationTestFactory(
    testDsl: TransformationTestDsl6<Result, Input1, Input2, Input3, Input4, Input5, Input6>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(
            testDsl.input1.className,
            testDsl.input2.className,
            testDsl.input3.className,
            testDsl.input4.className,
            testDsl.input5.className,
            testDsl.input6.className
        ).joinToString(" and ")
        val testName = config.testName ?: "transformation $inputs to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(
                this,
                testDsl.input1,
                testDsl.input2,
                testDsl.input3,
                testDsl.input4,
                testDsl.input5,
                testDsl.input6,
            ) shouldBe result
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7> transformationTestFactory(
    testDsl: TransformationTestDsl7<Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(
            testDsl.input1.className,
            testDsl.input2.className,
            testDsl.input3.className,
            testDsl.input4.className,
            testDsl.input5.className,
            testDsl.input6.className,
            testDsl.input7.className
        ).joinToString(" and ")
        val testName = config.testName ?: "transformation $inputs to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(
                this,
                testDsl.input1,
                testDsl.input2,
                testDsl.input3,
                testDsl.input4,
                testDsl.input5,
                testDsl.input6,
                testDsl.input7,
            ) shouldBe result
        }
    }
}

internal fun <Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8> transformationTestFactory(
    testDsl: TransformationTestDsl8<Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8>,
    config: TransformationTestConfig,
    transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
): TestFactory {
    return stringSpec {
        val inputs = listOf(
            testDsl.input1.className,
            testDsl.input2.className,
            testDsl.input3.className,
            testDsl.input4.className,
            testDsl.input5.className,
            testDsl.input6.className,
            testDsl.input7.className,
            testDsl.input8.className
        ).joinToString(" and ")
        val testName = config.testName ?: "transformation $inputs to ${testDsl.resultType?.simpleName}"
        testName.config(this, config) {
            val result = testDsl.expectedResultLazy.value
            transformBlock(
                this,
                testDsl.input1,
                testDsl.input2,
                testDsl.input3,
                testDsl.input4,
                testDsl.input5,
                testDsl.input6,
                testDsl.input7,
                testDsl.input8,
            ) shouldBe result
        }
    }
}