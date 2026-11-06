package io.github.nakulov.kotestkit.exception

import io.kotest.core.Tag
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.DslDrivenSpec
import io.kotest.core.test.EnabledIf
import io.kotest.core.test.EnabledOrReasonIf
import io.kotest.core.test.TestCaseSeverityLevel
import kotlin.time.Duration


data class ShouldThrowTestDsl(
    val specDsl: DslDrivenSpec,
    val caller: ShouldThrowCaller,
    val defaultTestName: String
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
        throwBlock: suspend () -> Any?
    ) {
        specDsl.include(
            shouldThrowFactory(
                ShouldThrowTestConfig(
                    testName ?: defaultTestName,
                    enabled,
                    invocations,
                    tags,
                    timeout,
                    extensions,
                    enabledIf,
                    invocationTimeout,
                    severity,
                    enabledOrReasonIf,
                    blockingTest,
                    coroutineTestScope
                ),
                caller,
                throwBlock
            )
        )
    }
}