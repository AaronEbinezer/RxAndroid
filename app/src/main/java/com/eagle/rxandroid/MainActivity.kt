package com.eagle.rxandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.eagle.rxandroid.databinding.ActivityMainBinding
import com.eagle.rxandroid.repo.MainRepo
import com.eagle.rxandroid.utils.AppUtils
import com.eagle.rxandroid.vmodel.MainFactoryViewModel
import com.eagle.rxandroid.vmodel.MainViewModel
import com.eagle.rxandroid.vmodel.model.maps.Order
import com.eagle.rxandroid.vmodel.model.maps.OrderLine
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private var mainViewModel: MainViewModel? = null
    private var binding: ActivityMainBinding? = null
    private val order = Order(
        listOf(OrderLine("Tomato", 2), OrderLine("Garlic", 3), OrderLine("Chives", 2))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initializeObjects()
        observeGreetings()
        mainViewModel?.loadGreeting()
//        callRxSimpleApi()
//        callCryptoApi()
        mainViewModel?.loadList()
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

    private fun initializeObjects() {
        val factory by lazy {
            MainFactoryViewModel(MainRepo())
        }
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun callRxSimpleApi() {
        mainViewModel?.callSimpleApi()
    }

    private fun callCryptoApi() {
        mainViewModel?.cryptoCoin()
    }

}