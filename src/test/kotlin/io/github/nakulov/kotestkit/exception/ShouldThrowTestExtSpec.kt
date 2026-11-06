package io.github.nakulov.kotestkit.exception

import io.github.nakulov.kotestkit.testing.runBehaviorSpec
import io.github.nakulov.kotestkit.testing.runStringSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ShouldThrowTestExtSpec : StringSpec({
    "StringSpec: каждый вызов регистрирует по одному успешному тесту" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException> { error("x") }
            shouldThrowTest<IllegalStateException>().config(testName = "builder") { error("x") }
            shouldThrowWithMessageTest<IllegalStateException>("x") { error("x") }
            shouldThrowExactlyTest<IllegalStateException> { error("x") }
        }

        run.tests.map { it.result.isSuccess } shouldBe listOf(true, true, true, true)
    }

    "BehaviorSpec: каждый вызов регистрирует по одному успешному тесту" {
        val run = runBehaviorSpec {
            shouldThrowTest<IllegalStateException> { error("x") }
            shouldThrowTest<IllegalStateException>().config(testName = "builder") { error("x") }
            shouldThrowWithMessageTest<IllegalStateException>("x") { error("x") }
            shouldThrowExactlyTest<IllegalStateException> { error("x") }
        }

        run.tests.map { it.result.isSuccess } shouldBe listOf(true, true, true, true)
    }

    "StringSpec: тест падает, если исключения нет" {
        val run = runStringSpec {
            shouldThrowTest<IllegalStateException> { }
        }

        run.tests.single().result.isFailure shouldBe true
    }

    "BehaviorSpec: тест падает, если исключения нет" {
        val run = runBehaviorSpec {
            shouldThrowTest<IllegalStateException> { }
        }

        run.tests.single().result.isFailure shouldBe true
    }
})
