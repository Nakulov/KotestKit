package io.github.nakulov.kotestkit

import io.kotest.core.spec.style.scopes.BehaviorSpecGivenContainerScope
import io.kotest.core.spec.style.scopes.BehaviorSpecWhenContainerScope
import io.kotest.core.test.TestScope

suspend fun BehaviorSpecGivenContainerScope.`when`(
    test: suspend BehaviorSpecWhenContainerScope.() -> Unit
) {
    When("call test class", test)
}

suspend fun BehaviorSpecWhenContainerScope.then(
    test: suspend TestScope.() -> Unit
) {
    Then("should verify result", test)
}