package io.github.nakulov.kotestkit.transformation

import io.kotest.core.Tag
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.style.scopes.BehaviorSpecContextContainerScope
import io.kotest.core.test.EnabledIf
import io.kotest.core.test.EnabledOrReasonIf
import io.kotest.core.test.TestCaseSeverityLevel
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe
import kotlin.reflect.KClass
import kotlin.time.Duration

data class TransformationTestDsl<Result>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.() -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope,
                ),
                transformBlock = transformBlock
            )
        )
    }
}

data class TransformationTestDslWithData<Result>(
    val specDsl: BehaviorSpecContextContainerScope,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
) {

    suspend fun config(
        transformBlock: suspend TestScope.() -> Result,
    ) {
        specDsl.transformBlock() shouldBe expectedResultLazy.value
    }
}

class TransformationTestDsl1<Result, Input>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
    val input: Input
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.(Input) -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope,
                ),
                transformBlock = transformBlock
            )
        )
    }
}

data class TransformationTestDsl2<Result, Input1, Input2>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
    val input1: Input1,
    val input2: Input2,
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.(Input1, Input2) -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope
                ),
                transformBlock = transformBlock
            )
        )
    }
}

data class TransformationTestDsl3<Result, Input1, Input2, Input3>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
    val input1: Input1,
    val input2: Input2,
    val input3: Input3,
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.(Input1, Input2, Input3) -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope
                ),
                transformBlock = transformBlock
            )
        )
    }
}

data class TransformationTestDsl4<Result, Input1, Input2, Input3, Input4>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
    val input1: Input1,
    val input2: Input2,
    val input3: Input3,
    val input4: Input4
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4) -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope
                ),
                transformBlock = transformBlock
            )
        )
    }
}

data class TransformationTestDsl5<Result, Input1, Input2, Input3, Input4, Input5>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
    val input1: Input1,
    val input2: Input2,
    val input3: Input3,
    val input4: Input4,
    val input5: Input5
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5) -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope
                ),
                transformBlock = transformBlock
            )
        )
    }
}

data class TransformationTestDsl6<Result, Input1, Input2, Input3, Input4, Input5, Input6>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
    val input1: Input1,
    val input2: Input2,
    val input3: Input3,
    val input4: Input4,
    val input5: Input5,
    val input6: Input6
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6) -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope
                ),
                transformBlock = transformBlock
            )
        )
    }
}

data class TransformationTestDsl7<Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
    val input1: Input1,
    val input2: Input2,
    val input3: Input3,
    val input4: Input4,
    val input5: Input5,
    val input6: Input6,
    val input7: Input7
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7) -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope
                ),
                transformBlock = transformBlock
            )
        )
    }
}

data class TransformationTestDsl8<Result, Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8>(
    val specDsl: AbstractSpec,
    val expectedResultLazy: Lazy<Result>,
    val resultType: KClass<*>?,
    val input1: Input1,
    val input2: Input2,
    val input3: Input3,
    val input4: Input4,
    val input5: Input5,
    val input6: Input6,
    val input7: Input7,
    val input8: Input8
) {

    fun config(
        testName: String? = null,
        enabled: Boolean? = null,
        invocations: Int? = null,
        tags: Set<Tag> = emptySet(),
        timeout: Duration? = null,
        extensions: List<TestCaseExtension>? = null,
        enabledIf: EnabledIf? = null,
        invocationTimeout: Duration? = null,
        severity: TestCaseSeverityLevel? = null,
        enabledOrReasonIf: EnabledOrReasonIf? = null,
        blockingTest: Boolean? = null,
        coroutineTestScope: Boolean? = null,
        transformBlock: suspend TestScope.(Input1, Input2, Input3, Input4, Input5, Input6, Input7, Input8) -> Result,
    ) {
        specDsl.include(
            transformationTestFactory(
                testDsl = this,
                config = TransformationTestConfig(
                    testName = testName,
                    enabled = enabled,
                    invocations = invocations,
                    tags = tags,
                    timeout = timeout,
                    extensions = extensions,
                    enabledIf = enabledIf,
                    invocationTimeout = invocationTimeout,
                    severity = severity,
                    enabledOrReasonIf = enabledOrReasonIf,
                    blockingTest = blockingTest,
                    coroutineTestScope = coroutineTestScope
                ),
                transformBlock = transformBlock
            )
        )
    }
}