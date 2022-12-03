package com.eagle.rxandroid.repo

import com.eagle.rxandroid.api.ApiService
import com.eagle.rxandroid.vmodel.model.CheckRxApiModel
import com.eagle.rxandroid.vmodel.model.CurrencyModel
import com.eagle.rxandroid.vmodel.model.maps.Order
import com.eagle.rxandroid.vmodel.model.maps.OrderLine
import io.reactivex.Single

class MainRepo() {
    fun callRxApi(apiService: ApiService?): Single<List<CurrencyModel.Market>>? {
        return apiService?.getCryptoCoinData("btc")?.flatMap { cryptoModel ->
            Single.just(cryptoModel.ticker?.markets)
        }
    }

    fun callRxSimpleApi(apiService: ApiService?): Single<String>? {
        return apiService?.simpleApi()?.flatMap { simpleRes ->
            Single.just(simpleRes.data)
        }
    }

    fun getOrderList(apiService: ApiService?): Single<List<OrderLine>>? {
        return apiService?.getOrder()?.map { item -> item.flatMap { order ->
            order.lines
        } }?.flatMap { orderLine ->
            Single.just(orderLine)
        }
    }
}