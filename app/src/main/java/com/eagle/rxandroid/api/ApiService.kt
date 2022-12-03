package com.eagle.rxandroid.api

import com.eagle.rxandroid.vmodel.model.CheckRxApiModel
import com.eagle.rxandroid.vmodel.model.CurrencyModel
import com.eagle.rxandroid.vmodel.model.maps.Order
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("v1/rx/android")
    fun simpleApi(): Single<CheckRxApiModel>?

    @GET("{coin}-usd")
    fun getCryptoCoinData(@Path("coin") coin: String?): Single<CurrencyModel>?

    @GET("v1/orders")
    fun getOrder(): Single<List<Order>>?
}