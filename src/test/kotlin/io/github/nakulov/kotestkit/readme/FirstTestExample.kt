package io.github.nakulov.kotestkit.readme

import io.github.nakulov.kotestkit.shouldReturnTest
import io.kotest.core.spec.style.BehaviorSpec

class Greeter {
    fun greet(name: String): String = "Привет, $name!"
}

class GreeterTest : BehaviorSpec({
    val greeter = Greeter()

    shouldReturnTest("Привет, Аня!") { greeter.greet("Аня") }
})
