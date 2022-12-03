package com.eagle.rxandroid.sample

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Test

class CarTest {
    val car = mockk<Car>()
    val car1 = spyk(Car())

    @Test
    fun testCar() {
        every { car.startCar() }.answers {
            it.equals(true)
        }
    }

    @Test
    fun testCar1() {
        assertTrue(car1.startCar())
    }
}