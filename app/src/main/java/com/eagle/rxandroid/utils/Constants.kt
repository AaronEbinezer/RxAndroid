package com.eagle.rxandroid.utils

class Constants {
    companion object {
//        const val BASE_URL = "https://api.cryptonator.com/api/full/"
        const val BASE_URL = "https://demo8648070.mockable.io/"
    }

}

enum class TaxClass(val percent: Int) {
    ONE(30), TWO(35), THREE(20)
}