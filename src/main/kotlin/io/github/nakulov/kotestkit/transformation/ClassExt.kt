package io.github.nakulov.kotestkit.transformation

internal val Any?.className get() = this?.let { this::class.simpleName } ?: "null"