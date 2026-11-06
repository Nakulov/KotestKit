package io.github.nakulov.kotestkit.exception

import io.github.nakulov.kotestkit.INSIDE_TEST_MESSAGE
import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.displayValue
import io.github.nakulov.kotestkit.registerLeaf
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.TestScope

inline fun <reified T : Throwable> AbstractSpec.shouldThrowTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrow<T> { throwBlock() }
    }
    val defaultTestName = "throws ${T::class.simpleName}"
    this.include(shouldThrowFactory(defaultTestName, options, caller, block))
}

inline fun <reified T : Throwable> AbstractSpec.shouldThrowWithMessageTest(
    message: String,
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrowWithMessage<T>(message) { throwBlock() }
    }
    val defaultTestName = "throws ${T::class.simpleName}: ${displayValue(message)}"
    this.include(shouldThrowFactory(defaultTestName, options, caller, block))
}

inline fun <reified T : Throwable> AbstractSpec.shouldThrowExactlyTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrowExactly<T> { throwBlock() }
    }
    val defaultTestName = "throws exactly ${T::class.simpleName}"
    this.include(shouldThrowFactory(defaultTestName, options, caller, block))
}

suspend inline fun <reified T : Throwable> ContainerScope.shouldThrowTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    registerLeaf("throws ${T::class.simpleName}", options) {
        shouldThrow<T> { block() }
    }
}

suspend inline fun <reified T : Throwable> ContainerScope.shouldThrowWithMessageTest(
    message: String,
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    registerLeaf("throws ${T::class.simpleName}: ${displayValue(message)}", options) {
        shouldThrowWithMessage<T>(message) { block() }
    }
}

suspend inline fun <reified T : Throwable> ContainerScope.shouldThrowExactlyTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend () -> Any?,
) {
    registerLeaf("throws exactly ${T::class.simpleName}", options) {
        shouldThrowExactly<T> { block() }
    }
}

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <T : Throwable> TestScope.shouldThrowTest(
    options: TestOptions = TestOptions(),
    block: suspend () -> Any?,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <T : Throwable> TestScope.shouldThrowWithMessageTest(
    message: String,
    options: TestOptions = TestOptions(),
    block: suspend () -> Any?,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <T : Throwable> TestScope.shouldThrowExactlyTest(
    options: TestOptions = TestOptions(),
    block: suspend () -> Any?,
): Unit = error("unreachable")
