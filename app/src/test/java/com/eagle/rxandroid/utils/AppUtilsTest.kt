package com.eagle.rxandroid.utils

import org.junit.Assert.*
import org.junit.Test
class AppUtilsTest {
    @Test
    fun validateNo() {
        val isValidNo = AppUtils.getInstance().isValidNo(6)
        assertTrue(isValidNo)
        val isInValidNo = AppUtils.getInstance().isValidNo(1)
        assertFalse(isInValidNo)
    }
}