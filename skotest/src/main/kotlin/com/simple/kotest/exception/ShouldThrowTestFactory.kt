package com.simple.kotest.exception

import io.kotest.core.factory.TestFactory
import io.kotest.core.spec.style.stringSpec

fun shouldThrowFactory(
    config: ShouldThrowTestConfig,
    caller: ShouldThrowCaller,
    throwBlock: suspend () -> Any?
): TestFactory {
    return stringSpec {
        val testName = config.testName
        testName.config(this, config) {
            caller.call(throwBlock)
        }
    }
}