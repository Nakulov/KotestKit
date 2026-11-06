package io.github.nakulov.kotestkit.exception

fun interface ShouldThrowCaller {

    suspend fun call(block: suspend () -> Any?)
}