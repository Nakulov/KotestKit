package io.github.nakulov.kotestkit.transformation

import io.kotest.core.spec.style.scopes.StringSpecRootScope
import io.kotest.core.test.TestScope

internal fun String.config(
    scope: StringSpecRootScope,
    config: TransformationTestConfig,
    test: suspend TestScope.() -> Unit,
) {
    with(scope) { config(config.kotestConfig, test) }
}