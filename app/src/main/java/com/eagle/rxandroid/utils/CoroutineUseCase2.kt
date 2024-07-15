package com.eagle.rxandroid.utils

import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

fun main() = runBlocking<Unit> {
    launch {
//        sequenceFun()
        concurrentFun()
    }
    println("Ends")
}

private suspend fun sequenceFun() {
    println("Time ${System.currentTimeMillis().time()}")
    val result1 = response1(1)
    val result2 = response1(2)
    val res = result1 + result2
    println("$res ${System.currentTimeMillis().time()}")
}

private suspend fun concurrentFun() {
    val job = GlobalScope.launch {
        println("Time ${System.currentTimeMillis().time()}")
        val result1 = async {
            response1(1)
        }
        val result2 = async {
            response1(2)
        }

//        val res = result1.await() + result2.await()
//        println("$res ${System.currentTimeMillis().time()}")
    }
    job.join()
    println("Ends")
}

suspend fun response1(work: Int = 1): String {
    println("work $work starts")
    delay(1000)
    println("work $work ends")
    return "result 1"
}

fun Long.time(): String {
    return String.format(
        "%02d min, %02d sec",
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
    )
}