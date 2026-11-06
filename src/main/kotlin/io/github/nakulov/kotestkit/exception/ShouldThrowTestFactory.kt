package io.github.nakulov.kotestkit.exception

import io.github.nakulov.kotestkit.TestOptions
import io.kotest.core.factory.TestFactory
import io.kotest.core.spec.style.stringSpec

@PublishedApi
internal fun shouldThrowFactory(
    defaultTestName: String,
    options: TestOptions,
    caller: ShouldThrowCaller,
    throwBlock: suspend () -> Any?
): TestFactory {
    return stringSpec {
        val testName = options.name ?: defaultTestName
        testName.config(this, options.config) {
            caller.call(throwBlock)
        }
    }
}
