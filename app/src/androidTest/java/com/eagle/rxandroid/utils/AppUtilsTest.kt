package com.eagle.rxandroid.utils

import android.content.Context
import android.provider.Settings
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

class AppUtilsTest {
    @Test
    fun validatePhoneNo() {
        val isValidPhoneNo = AppUtils.getInstance().validatePhoneNo("1234567890")
        assertTrue(isValidPhoneNo)
    }

}