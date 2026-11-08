package io.github.nakulov.kotestkit.serialization

import io.github.nakulov.kotestkit.TestOptions
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.common.KotestInternal
import io.kotest.core.names.TestNameBuilder
import io.kotest.core.spec.TestDefinitionBuilder
import io.kotest.core.spec.style.TestXMethod
import io.kotest.core.spec.style.scopes.BehaviorSpecRootScope
import io.kotest.core.test.TestScope
import io.kotest.core.test.TestType
import io.kotest.matchers.shouldBe
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

@PublishedApi
internal fun decodeTest(
    spec: BehaviorSpecRootScope,
    json: Json,
    resource: String,
    expected: Any,
    typeName: String?,
    options: TestOptions,
    serializer: () -> KSerializer<*>,
) {
    register(spec, options, "$resource -> ${typeName ?: "<anonymous>"}") {
        json.decodeFromString(serializer(), readResource(resource)) shouldBe expected
    }
}

@Suppress("UNCHECKED_CAST")
@PublishedApi
internal fun encodeTest(
    spec: BehaviorSpecRootScope,
    json: Json,
    value: Any,
    resource: String,
    typeName: String?,
    options: TestOptions,
    serializer: () -> KSerializer<*>,
) {
    register(spec, options, "${typeName ?: "<anonymous>"} -> $resource") {
        json.encodeToString(serializer() as KSerializer<Any>, value) shouldEqualJson readResource(resource)
    }
}

/** Ставит место вызова первым в стек: ошибки kotlinx и сравнения иначе не ведут к строке спеки. */
@OptIn(KotestInternal::class)
private fun register(spec: BehaviorSpecRootScope, options: TestOptions, defaultName: String, test: suspend TestScope.() -> Unit) {
    val site = callSite()
    val definition = TestDefinitionBuilder.builder(TestNameBuilder.builder(options.name ?: defaultName).build(), TestType.Test)
        .withXmethod(TestXMethod.NONE)
        .withConfig(options.config)
        .build {
            try {
                test()
            } catch (e: Throwable) {
                e.stackTrace = arrayOf(site) + e.stackTrace
                throw e
            }
        }
    spec.add(definition)
}

/** Ресурс classpath от корня `src/test/resources`; ведущий `/` допускается. */
private fun readResource(path: String): String {
    val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(path.removePrefix("/"))
        ?: throw AssertionError("resource $path not found")
    return stream.use { it.readBytes().decodeToString() }
}
