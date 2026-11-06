package io.github.nakulov.kotestkit

import io.kotest.core.test.config.TestConfig

class TestOptions(
    /** Имя теста, `null` означает имя по умолчанию. */
    val name: String? = null,
    val config: TestConfig = TestConfig(),
)
