package io.github.nakulov.kotestkit

import io.kotest.assertions.print.print

private const val MAX_VALUE_LENGTH = 40

private val OBJECT_ADDRESS = Regex("""\b(?:[a-z_]\w*\.)+([A-Z][\w$]*)(?:/0x[0-9a-f]+)?@[0-9a-f]+\b""")

/** Классы библиотеки на пути от вызова до регистрации. Сравниваются точно: спеки лежат в тех же пакетах. */
private val LIBRARY_CLASSES = setOf(
    "io.github.nakulov.kotestkit.TestReportKt",
    "io.github.nakulov.kotestkit.ShouldReturnTestExtKt",
)

/** Показ значения в имени теста: как у `print()` из Kotest, но без адресов объектов и не длиннее лимита. */
@PublishedApi
internal fun displayValue(value: Any?): String {
    val printed = printOrClassName(value)
    val shown = if (value is String) printed else OBJECT_ADDRESS.replace(printed) { shortClassName(it.groupValues[1]) }
    return if (shown.length > MAX_VALUE_LENGTH) shown.take(MAX_VALUE_LENGTH) + "…" else shown
}

/** Ловит `Throwable`: циклический `toString` даёт `StackOverflowError`, а спека не должна терять все тесты. */
private fun printOrClassName(value: Any?): String = try {
    value.print().value
} catch (_: Throwable) {
    value?.let { it::class.simpleName } ?: "<anonymous>"
}

private fun shortClassName(className: String): String {
    val lastPart = className.substringAfterLast('$')
    return when {
        "\$Lambda" in className -> "<lambda>"
        lastPart.isEmpty() || lastPart.first().isDigit() -> "<anonymous>"
        else -> lastPart
    }
}

/** Первый кадр вне библиотеки. Вызывать при регистрации, пока в стеке есть вызов из спеки. */
internal fun callSite(): StackTraceElement =
    Throwable().stackTrace.first { it.className.substringBefore('$') !in LIBRARY_CLASSES }
