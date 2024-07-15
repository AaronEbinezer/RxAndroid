package com.eagle.rxandroid.vmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eagle.rxandroid.api.ApiService
import com.eagle.rxandroid.getOrAwaitValue
import com.eagle.rxandroid.repo.MainRepo
import com.eagle.rxandroid.utils.RxImmediateSchedulerRule
import com.eagle.rxandroid.vmodel.model.CheckRxApiModel
import io.mockk.*
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxImmediateSchedulerRule()

//    private val apiService = mockk<ApiService>() {
//        val checkRxApiModel = mockk<CheckRxApiModel>()
//        coEvery { simpleApi() } returns (Single.just(checkRxApiModel))
//    }
//
//    private val mockRepository: MainRepo = mockk {
//        coEvery { callRxSimpleApi(apiService) } returns (Single.just("Market value"))
//    }

    @Mock
    private lateinit var apiService: ApiService

    //    @Mock
    private lateinit var mockRepository: MainRepo

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var mockObserver: Observer<String>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        MockitoAnnotations.initMocks(this)

//        mockRepository = mock(MainRepo::class.java)
//        apiService = mock(ApiService::class.java)
        //setup view model with dependencies mocked
        mockRepository = MainRepo(apiService)
        viewModel = MainViewModel(repo = mockRepository)
        viewModel.getMarketValue().observeForever(mockObserver as Observer<in String>)
    }


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


    @Test
    fun `test greetings observer`() {

        viewModel.loadGreeting()
        val value = viewModel.getGreetings().getOrAwaitValue()
        println("value $value")
        assertEquals("Hello from RX Android", value)

    }

    @Test
    fun test_market_value_observer() {
        val observer = Observer<String> {
            val value = it
            assertEquals("Success", value)
        }
        `when`(apiService.simpleApi()).thenReturn(Single.just(CheckRxApiModel(true, "Success")))
        viewModel.callSimpleSingleApi()
        try {
            viewModel.getMarketValue().observeForever(observer)
        } finally {
            viewModel.getMarketValue().removeObserver(observer)
        }

//        doReturn(apiService).`when`(RetroClient.getApiService())
//        `when`(mockRepository.callRxSimpleApi(apiService)).thenReturn(Single.just(""))
//        doReturn(Single.just("")).`when`(mockRepository).callRxSimpleApi()
//        testObserver.awaitTerminalEvent()
//        testObserver.onComplete()
        /*coEvery { dataRepository.requestRecipes() } returns flow {
            emit(Resource.Success(recipesModel))
        }*/

//        Mockito.verify(mockObserver).onChanged(any())
//        val value = viewModel.getMarketValue().getOrAwaitValue()
//        println("value $value")

//        val value = viewModel.getMarketValue().getOrAwaitValue()
//        assertEquals("Market value", value)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}