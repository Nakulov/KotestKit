package io.github.nakulov.kotestkit.transformation

@PublishedApi
internal val Any?.className get() = this?.let { this::class.simpleName } ?: "null"