package com.eagle.rxandroid.utils

import android.text.TextUtils

class AppUtils private constructor() {
    companion object {
        private var instance: AppUtils? = null

        @JvmName("getInstance")
        fun getInstance(): AppUtils {
            if(instance == null) instance = AppUtils()
            return instance!!
        }
    }

    fun validatePhoneNo(phoneNoStr: String): Boolean {
        return !TextUtils.isEmpty(phoneNoStr) && phoneNoStr.length == 10 &&
                TextUtils.isDigitsOnly(phoneNoStr)
    }

    fun isValidNo(no: Int): Boolean {
        return no > 5
    }

}