package io.github.nakulov.kotestkit.serialization

import io.kotest.core.spec.style.scopes.BehaviorSpecRootScope
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.TestScope
import kotlinx.serialization.json.Json

private const val WITH_JSON_MESSAGE = "withJson работает только в корне спеки"

/** Группа тестов сериализации. Сама тест не создаёт: каждый вызов внутри регистрирует один тест в корне спеки. */
fun BehaviorSpecRootScope.withJson(json: Json = DefaultTestJson, block: JsonTestScope.() -> Unit) {
    JsonTestScope(this, json).block()
}

@Deprecated(WITH_JSON_MESSAGE, level = DeprecationLevel.ERROR)
fun ContainerScope.withJson(json: Json = DefaultTestJson, block: JsonTestScope.() -> Unit): Unit =
    throw UnsupportedOperationException(WITH_JSON_MESSAGE)

@Deprecated(WITH_JSON_MESSAGE, level = DeprecationLevel.ERROR)
fun TestScope.withJson(json: Json = DefaultTestJson, block: JsonTestScope.() -> Unit): Unit =
    throw UnsupportedOperationException(WITH_JSON_MESSAGE)
