package com.eagle.rxandroid

import android.content.Context
import android.content.res.Configuration
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eagle.rxandroid.api.RetroClient
import com.eagle.rxandroid.databinding.ActivityDummyBinding
import com.eagle.rxandroid.repo.MainRepo
import com.eagle.rxandroid.sealedclass.Dog
import com.eagle.rxandroid.sealedclass.ErrorSeverity
import com.eagle.rxandroid.sealedclass.SealedClass
import com.eagle.rxandroid.utils.Rx
import com.eagle.rxandroid.vmodel.MainFactoryViewModel
import com.eagle.rxandroid.vmodel.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import java.io.File
import java.time.LocalTime
import javax.sql.DataSource


open class DummyActivity : AppCompatActivity() {
    private var bindings : ActivityDummyBinding? = null
    internal val protectedProperty = 30
    val viewModel by lazy {
        ViewModelProvider(this, getFactoryViewModel())[MainViewModel::class.java]
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        println("******** asdfds")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        println("******** onRestoreInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("******** save")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        println("onRestoreInstanceState2")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityDummyBinding.inflate(layoutInflater)
        setContentView(bindings?.root)
        val sealedClass = com.eagle.rxandroid.sealedclass.Error.FileReadError(File(""))
        val error= com.eagle.rxandroid.sealedclass.Error.RuntimeError
        println(" asdfds onCreate")
        val rx = Rx()
        rx.imageDownload() {
//            bindings?.ivImage?.setImageBitmap(it)
        }
//        flatMapProcess()
//        CoroutineScope(Dispatchers.IO).launch {
//            Thread.currentThread().name
//        }
        runner()
    }


    private fun runner() {
        val myHandlerThread = MyHandlerThread()
        myHandlerThread.start()

        // Wait for the Handler to be initialized
        while (myHandlerThread.handler?.looper == null) {
            Thread.sleep(10)
        }

        // Create and send a message
        val message = Message.obtain()
        message.obj = "Hello from main thread" // Set message content
        myHandlerThread.handler?.sendMessage(message)
    }

    private fun flatMapProcess() = runBlocking {
        val data = viewModel.generateData()
        var currentTime: LocalTime = LocalTime.now()
        println("Current time before map $currentTime")
        val price = data.ticker?.markets?.map { it.price }
        currentTime = LocalTime.now()
        println("Current time after map $currentTime")
        println("Mapped list ${Gson().toJson(price)}")
    }

    private fun getFactoryViewModel(): MainFactoryViewModel {
        return MainFactoryViewModel(MainRepo(RetroClient.getApiService()))
    }
}

class MyHandlerThread : Thread() {
    var handler: Handler?=null // Handler to handle messages

    override fun run() {
        // Initialize the Looper
        Looper.prepare()

        // Create a Handler associated with this thread's Looper
        handler = object : Handler(Looper.myLooper()!!) {
            override fun handleMessage(msg: Message) {
                // Handle incoming messages here
                println("Received Message: ${msg.obj} ${Thread.currentThread().name}")
            }
        }

        // Start the message loop
        Looper.loop()
    }


    class p {
        fun main() {
            val d: DummyActivity? = null
            d?.protectedProperty
        }
    }
}