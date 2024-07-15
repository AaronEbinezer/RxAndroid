package com.eagle.rxandroid.utils

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() {
//    exceptionHandleMethod()
//    deferredAsyncMethod()
    handleExceptionDeferredAsyncMethod()
}

private fun deferredAsyncMethod() = runBlocking {
    coroutineScope {
        val deferred = async {
            response(1)
        }
        launch {
//            deferred.await()
            response(2)
        }
    }
}

private fun handleExceptionDeferredAsyncMethod() = runBlocking {
    coroutineScope {
        val deferred1 = async {
            response(1)
            throw RuntimeException()
        }
        val deferred2 = async {
            response(2)
        }
        val deferred3 = async {
            response(3)
        }

        listOf(deferred1, deferred2, deferred3).map {
            try {
                it.await()
            } catch (e: RuntimeException) {
                println("Run time Exception")
            }
        }
    }
}

private fun exceptionHandleMethod() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        if (throwable is RuntimeException) {
            println("Runtime exception")
        }
    }

    CoroutineScope(Job() + exceptionHandler).launch {
        launch {
            throw RuntimeException()
        }
        launch {
            response(2)
        }
    }
}


suspend fun response(work: Int = 1): String {
    println("work $work starts")
    delay(1000)
    println("work $work ends")
    return "result 1"
}