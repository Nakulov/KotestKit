package com.simple.kotest.exception

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.DslDrivenSpec

inline fun <reified T : Throwable> DslDrivenSpec.shouldThrowTest(
    noinline block: suspend () -> Any?
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrow<T> { throwBlock() }
    }
    val testName = "should throw ${T::class.simpleName}"
    this.include(
        shouldThrowFactory(
            ShouldThrowTestConfig(testName),
            caller,
            block
        )
    )
}

inline fun <reified T : Throwable> DslDrivenSpec.shouldThrowTest(): ShouldThrowTestDsl {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrow<T> { throwBlock() }
    }
    val defaultTestName = "should throw ${T::class.simpleName}"
    return ShouldThrowTestDsl(this, caller, defaultTestName)
}

inline fun <reified T : Throwable> DslDrivenSpec.shouldThrowWithMessageTest(
    message: String,
    noinline block: suspend () -> Any?
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrowWithMessage<T>(message) { throwBlock() }
    }
    val testName = "should throw ${T::class.simpleName} with message \"$message\""
    this.include(
        shouldThrowFactory(
            ShouldThrowTestConfig(testName),
            caller,
            block
        )
    )
}

inline fun <reified T : Throwable> DslDrivenSpec.shouldThrowWithMessageTest(
    message: String
): ShouldThrowTestDsl {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrowWithMessage<T>(message) { throwBlock() }
    }
    val defaultTestName = "should throw ${T::class.simpleName} with message \"$message\""
    return ShouldThrowTestDsl(this, caller, defaultTestName)
}

inline fun <reified T : Throwable> DslDrivenSpec.shouldThrowExactlyTest(
    noinline block: suspend () -> Any?
) {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrowExactly<T> { throwBlock() }
    }
    val defaultTestName = "should throw ${T::class.simpleName} exactly"
    this.include(
        shouldThrowFactory(
            ShouldThrowTestConfig(defaultTestName),
            caller,
            block
        )
    )
}

inline fun <reified T : Throwable> DslDrivenSpec.shouldThrowExactlyTest(): ShouldThrowTestDsl {
    val caller = ShouldThrowCaller { throwBlock ->
        shouldThrowExactly<T> { throwBlock() }
    }
    val defaultTestName = "should throw ${T::class.simpleName} exactly"
    return ShouldThrowTestDsl(this, caller, defaultTestName)
}