package com.example.data.repository

import com.example.data.TestDispatcherProvider
import com.example.data.remote.ApiService
import com.example.data.utils.NetworkBoundResource
import com.example.domain.entity.PhotoDto
import com.example.domain.utils.Constants
import com.example.domain.utils.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import com.example.domain.utils.Result
import com.google.common.truth.Truth

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PhotoRepositoryImplTest {
    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var networkBoundResources: NetworkBoundResource
    private lateinit var photoRepositoryImpl: PhotoRepositoryImpl
    private lateinit var dispatcherProvider: DispatcherProvider
    private val photoDto = Mockito.mock(PhotoDto::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dispatcherProvider = TestDispatcherProvider()
        networkBoundResources = NetworkBoundResource(dispatcherProvider)
        photoRepositoryImpl = PhotoRepositoryImpl(apiService,networkBoundResources)
    }

    @Test
    fun `getPhotoDetails retun success`() {
        runTest {
            Mockito.`when`(apiService.getPhotoDetails())
                .thenAnswer { Response.success(photoDto) }
            val state = MutableStateFlow<Result<PhotoDto>>(Result.Success(photoDto))
            photoRepositoryImpl.getPhotoDetails().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Success)
        }
    }

    @Test
    fun `getPhotoDetails return error`() {
        runTest {
            Mockito.`when`(apiService.getPhotoDetails())
                .thenAnswer { Result.Error<PhotoDto>(message = "", code = 0) }

            val state = MutableStateFlow<Result<PhotoDto>>(Result.Error("", 0))
            photoRepositoryImpl.getPhotoDetails().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun `getPhotoDetails return unknown error`() {
        runTest {
            Mockito.`when`(apiService.getPhotoDetails())
                .thenReturn(Response.error(500, "".toResponseBody()))

            val state =
                MutableStateFlow<Result<PhotoDto>>(Result.Error(Constants.UNKNOWN_ERROR, 500))
            photoRepositoryImpl.getPhotoDetails().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun `getPhotoDetails return http exception`() {
        runTest {
            val httpException = Mockito.mock(HttpException::class.java)
            Mockito.`when`(apiService.getPhotoDetails())
                .thenThrow(httpException)
            val state =
                MutableStateFlow<Result<PhotoDto>>(
                    Result.Error(
                        Constants.SOMETHING_ERROR,
                        httpException.code()
                    )
                )
            photoRepositoryImpl.getPhotoDetails().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun `getPhotoDetails return socket exception`() {
        runTest {
            val httpException = Mockito.mock(HttpException::class.java)
            Mockito.`when`(apiService.getPhotoDetails())
                .thenThrow(httpException)
            val state =
                MutableStateFlow<Result<PhotoDto>>(
                    Result.Error(
                        Constants.CONNECTION_TIMEOUT,
                        httpException.code()
                    )
                )
            photoRepositoryImpl.getPhotoDetails().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }

    @Test
    fun `getPhotoDetails return IO exception`() {
        runTest {
            val httpException = Mockito.mock(HttpException::class.java)
            Mockito.`when`(apiService.getPhotoDetails())
                .thenThrow(httpException)
            val state =
                MutableStateFlow<Result<PhotoDto>>(
                    Result.Error(
                        Constants.NETWORK_ERROR,
                        httpException.code()
                    )
                )
            photoRepositoryImpl.getPhotoDetails().collect {
                state.value = it
            }
            Truth.assertThat(state.value is Result.Error)
        }
    }
}