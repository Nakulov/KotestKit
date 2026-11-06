package io.github.nakulov.kotestkit

import io.kotest.common.KotestInternal
import io.kotest.core.names.TestNameBuilder
import io.kotest.core.spec.TestDefinitionBuilder
import io.kotest.core.spec.style.TestXMethod
import io.kotest.core.spec.style.scopes.ContainerScope
import io.kotest.core.test.TestScope
import io.kotest.core.test.TestType

internal const val INSIDE_TEST_MESSAGE = "Нельзя вызывать внутри теста: перенесите вызов в контейнер или корень спеки"

/** Регистрирует в контейнере один конечный тест, как `test` и `Then` в Kotest. */
@PublishedApi
@OptIn(KotestInternal::class)
internal suspend fun ContainerScope.registerLeaf(
    name: String,
    options: TestOptions,
    test: suspend TestScope.() -> Unit,
) {
    registerTest(
        TestDefinitionBuilder.builder(TestNameBuilder.builder(options.name ?: name).build(), TestType.Test)
            .withXmethod(TestXMethod.NONE)
            .withConfig(options.config)
            .build(test)
    )
}
