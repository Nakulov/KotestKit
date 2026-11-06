package com.simple.kotest.transformation

import io.kotest.core.Tag
import io.kotest.core.extensions.Extension
import io.kotest.core.test.EnabledIf
import io.kotest.core.test.EnabledOrReasonIf
import io.kotest.core.test.TestCaseSeverityLevel
import io.kotest.core.test.config.TestConfig
import kotlin.time.Duration

internal data class TransformationTestConfig(
    val testName: String?,
    private val enabled: Boolean? = null,
    private val invocations: Int? = null,
    private val tags: Set<Tag> = emptySet(),
    private val timeout: Duration? = null,
    private val extensions: List<Extension>? = null,
    private val enabledIf: EnabledIf? = null,
    private val invocationTimeout: Duration? = null,
    private val severity: TestCaseSeverityLevel? = null,
    private val enabledOrReasonIf: EnabledOrReasonIf? = null,
    private val coroutineDebugProbes: Boolean? = null,
    private val blockingTest: Boolean? = null,
) {
    val kotestConfig = TestConfig(
        enabled = enabled,
        enabledIf = enabledIf,
        enabledOrReasonIf = enabledOrReasonIf,
        tags = tags,
        extensions = extensions,
        timeout = timeout,
        invocationTimeout = invocationTimeout,
        invocations = invocations,
        severity = severity,
        coroutineDebugProbes = coroutineDebugProbes,
        blockingTest = blockingTest,
    )
}