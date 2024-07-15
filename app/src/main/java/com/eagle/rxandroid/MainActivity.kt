package com.eagle.rxandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.eagle.rxandroid.api.RetroClient
import com.eagle.rxandroid.databinding.ActivityMainBinding
import com.eagle.rxandroid.repo.MainRepo
import com.eagle.rxandroid.utils.AppUtils
import com.eagle.rxandroid.utils.Rx
import com.eagle.rxandroid.vmodel.MainFactoryViewModel
import com.eagle.rxandroid.vmodel.MainViewModel
import com.eagle.rxandroid.vmodel.model.maps.Order
import com.eagle.rxandroid.vmodel.model.maps.OrderLine
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private val mainViewModel by lazy {
        ViewModelProvider(this, getFactoryViewModel())[MainViewModel::class.java]
    }
    private var binding: ActivityMainBinding? = null
    private val order = Order(
        listOf(OrderLine("Tomato", 2), OrderLine("Garlic", 3), OrderLine("Chives", 2))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//        observeGreetings()
//        mainViewModel.loadGreeting()
//        observeSimpleApi()
//        callSimpleSingleApi()
//        callRxSimpleApi()
//        callCryptoApi()
//        mainViewModel?.loadList()
//        mapFunc()
//        mainViewModel?.getOrderList()
    }
    private fun mapFunc() {
//       https://www.baeldung.com/kotlin/map-vs-flatmap
        val names = order.lines.map { it.name }
        println("Names list $names")
        totalOrderPrice()
    }

    private fun totalOrderPrice() {
        val totalPrice = order.lines.map { it.price }.sum()
        println("Total price $totalPrice")
        flatMapFunc()
    }

    private fun flatMapFunc() {
        val orders = listOf(
            Order(listOf(OrderLine("Garlic", 1), OrderLine("Chives", 2))),
            Order(listOf(OrderLine("Tomato", 1), OrderLine("Garlic", 2))),
            Order(listOf(OrderLine("Potato", 1), OrderLine("Chives", 2))),
        )
        println("Order list ${Gson().toJson(orders)}")
        val lines: List<OrderLine> = orders.flatMap { it.lines }
        println("Line list ${Gson().toJson(lines)}")
        val names = lines.map { it.name }.distinct()
        orders[0].lines[0].name = ""
        println("Names list $names")
        println("Org Name list ${Gson().toJson(orders)}")
    }

    private fun observeGreetings() {
        mainViewModel?.getGreetings()?.observe(this) {
            binding?.greetingTv?.text = it
        }
    }

    private fun getFactoryViewModel(): MainFactoryViewModel {
        return MainFactoryViewModel(MainRepo(RetroClient.getApiService()))
    }

    private fun callRxSimpleApi() {
        mainViewModel?.callSimpleApi()
    }


    private fun callSimpleSingleApi() {
        mainViewModel?.callSimpleSingleApi()
    }

    private fun callCryptoApi() {
        mainViewModel?.cryptoCoin()
    }

    private fun observeSimpleApi() {
        mainViewModel?.getMarketValue()?.observe(this) {
            if(it != null) {
                println("value $it")
            } else {
                print("Error")
            }
        }
    }

}
