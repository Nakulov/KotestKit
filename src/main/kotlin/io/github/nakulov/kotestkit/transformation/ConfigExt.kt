package io.github.nakulov.kotestkit.transformation

import io.kotest.core.spec.style.scopes.StringSpecRootScope
import io.kotest.core.test.TestScope
import io.kotest.core.test.config.TestConfig

internal fun String.config(
    scope: StringSpecRootScope,
    config: TestConfig,
    test: suspend TestScope.() -> Unit,
) {
    with(scope) { config(config, test) }
}
