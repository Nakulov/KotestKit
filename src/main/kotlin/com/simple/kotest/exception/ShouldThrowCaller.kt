package com.simple.kotest.exception

fun interface ShouldThrowCaller {

    suspend fun call(block: suspend () -> Any?)
}