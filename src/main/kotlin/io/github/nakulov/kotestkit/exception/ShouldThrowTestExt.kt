package io.github.nakulov.kotestkit.exception

import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.then
import io.github.nakulov.kotestkit.`when`
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.style.scopes.BehaviorSpecContextContainerScope

inline fun <reified T : Throwable> AbstractSpec.shouldThrowTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrow<T> { throwBlock() }
    }
    val defaultTestName = "should throw ${T::class.simpleName}"
    this.include(shouldThrowFactory(defaultTestName, options, caller, block))
}

suspend inline fun <reified T : Throwable> BehaviorSpecContextContainerScope.shouldThrowTest(
    noinline block: suspend () -> Any?
) {
    given("should throw ${T::class.simpleName}") {
        `when` {
            then {
                shouldThrow<T> {
                    block()
                }
            }
        }
    }
}

inline fun <reified T : Throwable> AbstractSpec.shouldThrowWithMessageTest(
    message: String,
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrowWithMessage<T>(message) { throwBlock() }
    }
    val defaultTestName = "should throw ${T::class.simpleName} with message \"$message\""
    this.include(shouldThrowFactory(defaultTestName, options, caller, block))
}

suspend inline fun <reified T : Throwable> BehaviorSpecContextContainerScope.shouldThrowWithMessageTest(
    message: String,
    noinline block: suspend () -> Any?
) {
    given("should throw ${T::class.simpleName} with message \"$message\"") {
        `when` {
            then {
                shouldThrowWithMessage<T>(message) {
                    block()
                }
            }
        }
    }
}

inline fun <reified T : Throwable> AbstractSpec.shouldThrowExactlyTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrowExactly<T> { throwBlock() }
    }
    val defaultTestName = "should throw ${T::class.simpleName} exactly"
    this.include(shouldThrowFactory(defaultTestName, options, caller, block))
}
