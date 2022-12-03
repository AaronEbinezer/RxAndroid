package com.eagle.rxandroid.sample

import com.eagle.rxandroid.sample.TestableObject.getDataFromDb
import io.mockk.*
import junit.framework.Assert.assertEquals
import org.junit.Test

class TestableServiceTest {

    @Test
    fun testCar() {// given
        val service = mockk<TestableService>()
        every { service.getDataFromDb("Expected Param") } returns "Expected Output"

        // when
        val result = service.getDataFromDb("Expected Param")

        // then
        verify { service.getDataFromDb("Expected Param") }
        assertEquals("Expected Output", result)
    }

    @Test
    fun givenServiceSpy_whenMockingOnlyOneMethod_thenOtherMethodsShouldBehaveAsOriginalObject() {
        // given
        val service = spyk<TestableService>()
        every { service.getDataFromDb(any()) } returns "Mocked Output"

        // when checking mocked method
        val firstResult = service.getDataFromDb("Any Param")

        // then
        assertEquals("Mocked Output", firstResult)

        // when checking not mocked method
        val secondResult = service.doSomethingElse("Any Param")

        // then
        assertEquals("I don't want to!", secondResult)
    }

    @Test
    fun givenRelaxedMock_whenCallingNotMockedMethod_thenReturnDefaultValue() {
        // given
        val service = mockk<TestableService>(relaxed = true)

        // when
        val result = service.getDataFromDb("Any Param")

        // then
        assertEquals("", result)
    }

    @Test
    fun givenObject_whenMockingIt_thenMockedMethodShouldReturnProperValue() {
        // given
        mockkObject(TestableObject)

        // when calling not mocked method
        val firstResult = getDataFromDb("Any Param")

        // then return real response
//        assertEquals(/* DB result */, firstResult)

        // when calling mocked method
        every { getDataFromDb(any()) } returns "Mocked Output"
        val secondResult = getDataFromDb("Any Param")

        // then return mocked response
        assertEquals("Mocked Output", secondResult)
    }
}