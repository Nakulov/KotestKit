package io.github.nakulov.kotestkit

import io.kotest.common.KotestInternal
import io.kotest.core.names.TestNameBuilder
import io.kotest.core.spec.TestDefinition
import io.kotest.core.spec.TestDefinitionBuilder
import io.kotest.core.spec.style.TestXMethod
import io.kotest.core.test.TestScope
import io.kotest.core.test.TestType

internal const val INSIDE_TEST_MESSAGE = "Нельзя вызывать внутри теста: перенесите вызов в контейнер или корень спеки"

/** Один конечный тест: корень спеки отдаёт его в `add`, контейнер в `registerTest`. */
@PublishedApi
@OptIn(KotestInternal::class)
internal fun leafTest(
    options: TestOptions,
    defaultName: () -> String,
    test: suspend TestScope.() -> Unit,
): TestDefinition =
    TestDefinitionBuilder.builder(TestNameBuilder.builder(options.name ?: defaultName()).build(), TestType.Test)
        .withXmethod(TestXMethod.NONE)
        .withConfig(options.config)
        .build(test)
