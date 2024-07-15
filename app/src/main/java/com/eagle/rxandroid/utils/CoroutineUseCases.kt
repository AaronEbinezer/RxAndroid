package com.eagle.rxandroid.utils

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.*

fun main() {
//    globalScopeFun1()
//    globalScopeFun2()
//    runBlockingFn()
//    runBlockingJobFn()
//    runBlockingLazyJobFn()

}

fun globalScopeFun1() {
    GlobalScope.launch {
        delay(300)
        println("Coroutine Started")
    }
    println("Main Ends")
}

fun globalScopeFun2() {
    GlobalScope.launch {
        delay(300)
        println("Coroutine Started")
    }
    Thread.sleep(500)
    println("Main Ends")
}

fun runBlockingFn() {
    runBlocking {
        launch {
            delay(500)
            println("Coroutine Started")
        }
        println("runBlocking Ends")
    }
    println("Main Ends")
}

fun runBlockingJobFn() {
    runBlocking {
        val job = launch {
            delay(500)
            println("Coroutine Started")
        }
        job.join()
        println("runBlocking Ends")
    }
    println("Main Ends")
}

fun runBlockingLazyJobFn() {
    runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            delay(500)
            println("Coroutine Started")
        }
        job.start()
        println("runBlocking Ends")
    }
    println("Main Ends")
}
