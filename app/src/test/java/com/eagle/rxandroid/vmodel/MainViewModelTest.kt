package com.eagle.rxandroid.vmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eagle.rxandroid.getOrAwaitValue
import com.eagle.rxandroid.repo.MainRepo
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `observe greetings`() {
        val viewModel = mockk<MainViewModel>(relaxed = true)
        viewModel.loadGreeting()
        verify { viewModel.loadGreeting() }
        val observer = Observer<String> {
            val value = it
            assertEquals("Hello from RX Android", value)
        }
        try {
            viewModel.getGreetings().observeForever(observer)
        } finally {
            viewModel.getGreetings().removeObserver(observer)
        }

    }


    @After
    fun tearDown() {
        unmockkAll()
    }
}