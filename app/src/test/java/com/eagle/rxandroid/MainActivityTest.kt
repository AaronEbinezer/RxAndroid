package com.eagle.rxandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eagle.rxandroid.utils.RxImmediateSchedulerRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxImmediateSchedulerRule()


}