package com.simple.kotest.transformation

internal val Any?.className get() = this?.let { this::class.simpleName } ?: "null"