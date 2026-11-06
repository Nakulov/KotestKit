package io.github.nakulov.kotestkit.exception

import io.kotest.core.spec.style.scopes.StringSpecRootScope
import io.kotest.core.test.TestScope

fun String.config(
    scope: StringSpecRootScope,
    config: ShouldThrowTestConfig,
    test: suspend TestScope.() -> Unit
) {
    with(scope) {
        with(config.kotestConfig) {
            config(
                enabled = enabled,
                invocations = invocations,
                tags = tags,
                timeout = timeout,
                enabledIf = enabledIf,
                invocationTimeout = invocationTimeout,
                severity = severity,
                enabledOrReasonIf = enabledOrReasonIf,
                coroutineDebugProbes = coroutineDebugProbes,
                blockingTest = blockingTest,
                test = test
            )
        }
    }
}