package io.github.nakulov.kotestkit.exception

@PublishedApi
internal fun interface ShouldThrowCaller {

    suspend fun call(block: suspend () -> Any?)
}
