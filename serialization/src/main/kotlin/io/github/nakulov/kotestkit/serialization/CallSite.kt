package io.github.nakulov.kotestkit.serialization

/** Классы модуля на пути от вызова до регистрации. */
private val LIBRARY_CLASSES = setOf(
    "io.github.nakulov.kotestkit.serialization.CallSiteKt",
    "io.github.nakulov.kotestkit.serialization.JsonTestKt",
    "io.github.nakulov.kotestkit.serialization.JsonTestScope",
)

/** `72#1:132,3` из раздела KotlinDebug: строки 132–134 класса — встроенный код вызова со строки 72. */
private val KOTLIN_DEBUG_LINE = Regex("""(\d+)#\d+(?:,(\d+))?:(\d+)(?:,(\d+))?""")

/**
 * Первый кадр вне модуля. Вызывать при регистрации, пока в стеке есть вызов из спеки.
 *
 * Вызовы `shouldDecodeTest`, `shouldEncodeTest` и `shouldRoundTripTest` встраиваются (`inline`), и у их кода в классе спеки синтетические номера
 * строк за концом файла. Строку вызова восстанавливает раздел KotlinDebug из SMAP класса.
 */
internal fun callSite(): StackTraceElement {
    val frame = Throwable().stackTrace.first { it.className.substringBefore('$') !in LIBRARY_CLASSES }
    val line = callerLine(frame) ?: return frame
    return StackTraceElement(frame.className, frame.methodName, frame.fileName, line)
}

private fun callerLine(frame: StackTraceElement): Int? {
    val classPath = frame.className.replace('.', '/') + ".class"
    val classFile = Thread.currentThread().contextClassLoader.getResourceAsStream(classPath) ?: return null
    val section = classFile.use { it.readBytes().decodeToString() }.substringAfter("*S KotlinDebug", "").substringAfter("*L\n").substringBefore("*E")
    return section.lineSequence().firstNotNullOfOrNull { KOTLIN_DEBUG_LINE.matchEntire(it.trim())?.inputLine(frame.lineNumber) }
}

/** Строка исходника для строки класса [outputLine] по правилам JSR-45, `null`, если запись её не покрывает. */
private fun MatchResult.inputLine(outputLine: Int): Int? {
    val (inputStart, repeat, outputStart, increment) = destructured
    val step = increment.toIntOrNull() ?: 1
    val offset = outputLine - outputStart.toInt()
    return if (offset in 0 until (repeat.toIntOrNull() ?: 1) * step) inputStart.toInt() + offset / step else null
}
