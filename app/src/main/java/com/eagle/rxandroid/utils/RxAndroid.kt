package com.eagle.rxandroid.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Looper
import com.eagle.rxandroid.AccessSpecs
import com.eagle.rxandroid.DummyActivity
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

fun main() {
//    coldObservables()
//    hotObservables()
//    zipObservable()
//    scopeFunctions()
//    rxObservable()
//    rxObservableZip()
//    rxSingle()
//    singleObserver()
}

class Rx() {
    fun imageDownload(callback: (Bitmap) -> Unit) {
        var disposable: Disposable? = null
        val observer = object : Observer<Bitmap?> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onError(e: Throwable) {
                disposable?.dispose()
            }

            override fun onComplete() {

            }

            override fun onNext(t: Bitmap) {
                callback(t)
                println("image $t")
            }

        }
        Observable.fromCallable { downloadImageAsBitmap("https://pngimg.com/d/android_logo_PNG27.png") }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.from(Looper.myLooper()))
            .subscribe(observer)
    }

    private fun downloadImageAsBitmap(imageUrl: String): Bitmap? {
        var connection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        try {
            val url = URL(imageUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.inputStream
                val byteArrayOutputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead)
                }
                val imageBytes = byteArrayOutputStream.toByteArray()
                return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            } else {
                // Handle unsuccessful response
                println("Failed to download image: HTTP ${connection.responseCode}")
            }
        } catch (e: Exception) {
            // Handle exception
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            inputStream?.close()
        }
        return null
    }
}


private fun singleObserver() {
    val single = Single.just("One")
    val single1 = Single.just("two")
    var disposable: Disposable? = null
    val observer = object : SingleObserver<String> {
        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onError(e: Throwable) {
            TODO("Not yet implemented")
        }

        override fun onSuccess(t: String) {
            disposable?.dispose()
            println("val $t")
        }
    }
    Single.zip(
        single,
        single1
    ) { one, two ->
        "$one $two"
    }.subscribe(observer)
}

private fun rxSingle() {
    val single1 = Single.just("Aaron")
    val single2 = Single.just("Ebinezer")
    val disposable = Single.zip(
        single1,
        single2
    ) { o, t ->
        "$o $t"
    }.subscribe({
        println("$it")
    }, {

    })
    disposable.dispose()
}

private fun rxObservableZip() {
    val single1 = Observable.just("Aaron")
    val single2 = Observable.just("Ebinezer")
    var di: Disposable? = null

    val observer = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            di = d
            println("sub")
        }

        override fun onError(e: Throwable) {
            TODO("Not yet implemented")
        }

        override fun onComplete() {
            di?.dispose()
            println("Completed")
        }

        override fun onNext(t: String) {
            println("val $t")
        }
    }
    Observable.zip(
        single1,
        single2
    ) { o, t ->
        println("$o $t")
        "$o $t"
    }.subscribe(observer)
}

private fun rxObservable() {

    val observable = Observable.just(1, 2, 3)
    var di: Disposable? = null
    val observer = object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            di = d
            println("sub")
        }

        override fun onError(e: Throwable) {
            TODO("Not yet implemented")
        }

        override fun onComplete() {
            di?.dispose()
            println("Completed")
        }

        override fun onNext(t: Int) {
            println("val $t")
        }
    }
    observable
        .map { it }
        .subscribeOn(Schedulers.io())
        .filter {
            it > 1
        }.subscribe(observer)
}

private fun scopeFunctions() {
    val arr1 = arrayOf(2, 8, 15)
    val arr2 = arrayOf(5, 9, 12, 17)
    val arr3 = arrayOfNulls<Int>(arr1.size + arr2.size)
    var i = 0
    var j = 0
    var k = 0
    while (i < arr1.size) {
        while (j < arr2.size && i < arr1.size) {
            if (arr1[i] < arr2[j]) {
                val (a, b) = addArray(arr3, arr1, k, i)
                i = b
                k = a
            } else if (arr1[i] > arr2[j]) {
                arr3[k] = arr2[j]
                k++
                j++
            } else if (arr1[i] == arr2[j]) {
                arr3[k] = arr1[i]
                k++
                i++
                arr3[k] = arr2[j]
                k++
                j++
            }
            if (i == arr1.size) {
                while (j < arr2.size) {
                    arr3[k] = arr2[j]
                    j++
                    k++
                }
                break
            }
        }
        if (j == arr2.size &&
            i < arr1.size
        ) {
            arr3[k] = arr1[i]
            i++
            k++
        }
    }
    println("array ${Gson().toJson(arr3)}")
}

private fun addArray(arr3: Array<Int?>, arr1: Array<Int>, pos1: Int, pos2: Int): Pair<Int, Int> {
    var tempPos1 = pos1
    var tempPos2 = pos2
    arr3[pos1] = arr1[pos2]
    return Pair(++tempPos1, ++tempPos2)
}

data class abc(var a: Int?, var b: Int?)

fun coldObservables() {
    val observable: Observable<Int> = Observable.create { emitter ->
        emitter.onNext(Random(1000).nextInt(1000))
        emitter.onNext(Random(1).nextInt(1))
        emitter.onNext(Random(1).nextInt(10))
        emitter.onComplete()
    }

    observable.subscribe { item -> println("Subscriber 1: $item") }
    observable.subscribe { item -> println("Subscriber 2: $item") }
}

fun hotObservables() {
    val subject = PublishSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(2)
    subject.onNext(3)

    subject.subscribe { item: Int -> println("Subscriber 1: $item") }
    subject.onNext(4)
    subject.onNext(5)

    subject.subscribe { item: Int -> println("Subscriber 2: $item") }
    subject.onNext(6)
    subject.onNext(7)

}

fun zipObservable() {
    val observable1 = Observable.just(arrayListOf(""))
    val observable2 = Observable.just(1)

    Observable.zip(
        observable1,
        observable2
    ) { t1, t2 ->
        Random(100).nextString(10, 26)

    }.doOnError {
        println("Error1")
    }.subscribe(
        { t ->
            println("value: $t")

        },
        {
            println("Error")
        }
    )


}

fun Random.nextString(letterCount: Int, arg: Int = 10): String {
    val string = java.lang.StringBuilder()
    for (i in 0..letterCount) {
        val asciiVal = Random.nextInt(arg)
        string.append(asciiVal.toChar().toString())
    }
    return string.toString()
}