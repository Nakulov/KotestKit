package io.github.nakulov.kotestkit.exception

import io.github.nakulov.kotestkit.INSIDE_TEST_MESSAGE
import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.displayValue
import io.github.nakulov.kotestkit.leafTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.AbstractSpec
import io.kotest.core.spec.TestDefinition
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.TestScope

@PublishedApi
internal inline fun <reified T : Throwable> throwsTest(
    options: TestOptions,
    noinline block: suspend TestScope.() -> Any?,
): TestDefinition =
    leafTest(options, { "throws ${T::class.simpleName}" }) { shouldThrow<T> { block() } }

@PublishedApi
internal inline fun <reified T : Throwable> throwsWithMessageTest(
    message: String,
    options: TestOptions,
    noinline block: suspend TestScope.() -> Any?,
): TestDefinition =
    leafTest(options, { "throws ${T::class.simpleName}: ${displayValue(message)}" }) {
        shouldThrowWithMessage<T>(message) { block() }
    }

@PublishedApi
internal inline fun <reified T : Throwable> throwsExactlyTest(
    options: TestOptions,
    noinline block: suspend TestScope.() -> Any?,
): TestDefinition =
    leafTest(options, { "throws exactly ${T::class.simpleName}" }) { shouldThrowExactly<T> { block() } }

inline fun <reified T : Throwable> AbstractSpec.shouldThrowTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend TestScope.() -> Any?,
) {
    add(throwsTest<T>(options, block))
}

inline fun <reified T : Throwable> AbstractSpec.shouldThrowWithMessageTest(
    message: String,
    options: TestOptions = TestOptions(),
    noinline block: suspend TestScope.() -> Any?,
) {
    add(throwsWithMessageTest<T>(message, options, block))
}

inline fun <reified T : Throwable> AbstractSpec.shouldThrowExactlyTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend TestScope.() -> Any?,
) {
    add(throwsExactlyTest<T>(options, block))
}

suspend inline fun <reified T : Throwable> ContainerScope.shouldThrowTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend TestScope.() -> Any?,
) {
    registerTest(throwsTest<T>(options, block))
}

suspend inline fun <reified T : Throwable> ContainerScope.shouldThrowWithMessageTest(
    message: String,
    options: TestOptions = TestOptions(),
    noinline block: suspend TestScope.() -> Any?,
) {
    registerTest(throwsWithMessageTest<T>(message, options, block))
}

suspend inline fun <reified T : Throwable> ContainerScope.shouldThrowExactlyTest(
    options: TestOptions = TestOptions(),
    noinline block: suspend TestScope.() -> Any?,
) {
    registerTest(throwsExactlyTest<T>(options, block))
}

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <T : Throwable> TestScope.shouldThrowTest(
    options: TestOptions = TestOptions(),
    block: suspend TestScope.() -> Any?,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <T : Throwable> TestScope.shouldThrowWithMessageTest(
    message: String,
    options: TestOptions = TestOptions(),
    block: suspend TestScope.() -> Any?,
): Unit = error("unreachable")

@Deprecated(INSIDE_TEST_MESSAGE, level = DeprecationLevel.ERROR)
fun <T : Throwable> TestScope.shouldThrowExactlyTest(
    options: TestOptions = TestOptions(),
    block: suspend TestScope.() -> Any?,
): Unit = error("unreachable")
