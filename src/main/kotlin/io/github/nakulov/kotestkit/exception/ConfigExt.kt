package io.github.nakulov.kotestkit.exception

import io.kotest.core.spec.style.scopes.StringSpecRootScope
import io.kotest.core.test.TestScope

fun String.config(
    scope: StringSpecRootScope,
    config: ShouldThrowTestConfig,
    test: suspend TestScope.() -> Unit
) {
    with(scope) { config(config.kotestConfig, test) }
}