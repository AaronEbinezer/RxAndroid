package com.eagle.rxandroid.vmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eagle.rxandroid.api.ApiService
import com.eagle.rxandroid.api.RetroClient
import com.eagle.rxandroid.repo.MainRepo
import com.eagle.rxandroid.vmodel.model.CurrencyModel
import com.eagle.rxandroid.vmodel.model.CurrencyModel.Market
import com.eagle.rxandroid.vmodel.model.maps.Order
import com.eagle.rxandroid.vmodel.model.maps.OrderLine
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val repo: MainRepo) : ViewModel() {
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private var greetingLiveData = MutableLiveData<String>()
    private fun getGreeting(): String {
        return "Hello from RX Android"
    }

    fun loadGreeting() {
        val observable = Observable.just(getGreeting())
            .subscribeOn(Schedulers.io())
            .onErrorReturn { e -> "Error $e" }
            .subscribe { greeting ->
                Log.d("TAG", "loadGreeting: $greeting")
                greetingLiveData.postValue(greeting)
            }
        if (observable != null) {
            compositeDisposable.add(observable)
        }
    }

    fun getGreetings() = greetingLiveData

    fun callSimpleApi() {
        val cryptocurrencyService: ApiService? =
            RetroClient.getApiService()
        val cryptoObservable = cryptocurrencyService?.let {
            repo.callRxSimpleApi(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(this::handleRxResults, this::handleError)
        }

        if (cryptoObservable != null) {
            compositeDisposable.add(cryptoObservable)
        }
    }

    private fun handleRxResults(marketList: String?) {
        if (marketList?.isNotEmpty() == true) {
            println("Market List $marketList")
        } else {
            println("Failed to load the list")
        }
    }

    fun cryptoCoin() {
        val cryptocurrencyService: ApiService? =
            RetroClient.getApiService()
        val cryptoObservable = repo.callRxApi(cryptocurrencyService)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(
                this::handleCryptoResults, this::handleError
            )
        if (cryptoObservable != null) {
            compositeDisposable.add(cryptoObservable)
        }
    }

    private fun handleCryptoResults(currencyModel: List<CurrencyModel.Market>?) {
        if (currencyModel != null) {
            println("Coins usd List ${Gson().toJson(currencyModel)}")
        } else {
            println("Failed to load the list")
        }
    }

    fun getOrderList() {
        val cryptocurrencyService: ApiService? =
            RetroClient.getApiService()
        val observable = repo.getOrderList(cryptocurrencyService)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                this::handleOrderLine, this::handleError
            )
        if (observable != null) {
            compositeDisposable.add(observable)
        }
    }

    private fun handleOrderLine(list: List<OrderLine>) {
        println("Order Line ${Gson().toJson(list)}")
    }

    override fun onCleared() {
        super.onCleared()
        clearDisposable()
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }

    fun loadList() {
        val orders = listOf(
            Order(listOf(OrderLine("Garlic", 1), OrderLine("Chives", 2))),
            Order(listOf(OrderLine("Tomato", 1), OrderLine("Garlic", 2))),
            Order(listOf(OrderLine("Potato", 1), OrderLine("Chives", 2))),
        )
        println("Order list ${Gson().toJson(orders)}")
        val observable = Observable.just(orders)
            .subscribeOn(Schedulers.io())
            ?.map { item ->
                item.flatMap {
                    it.lines
                }
            }?.doOnComplete {
                println("completed")
            }?.subscribe {
                println("Result ${Gson().toJson(it)}")
            }
        if (observable != null) {
            compositeDisposable.add(observable)
        }
    }

    private fun handleResults(marketList: List<Market>?) {
        if (marketList != null && marketList.isNotEmpty()) {
            println("Market List ${Gson().toJson(marketList)}")
        } else {
            println("Failed to load the list")
        }
    }

    private fun handleError(t: Throwable) {
        println("Error occurred ${t.cause}")
    }
}