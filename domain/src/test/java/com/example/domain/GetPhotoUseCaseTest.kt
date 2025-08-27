package com.example.domain

import com.example.domain.entity.PhotoDto
import com.example.domain.repository.PhotoRepository
import com.example.domain.usecase.GetPhotoUseCase
import com.example.domain.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import com.example.domain.utils.Result
import com.google.common.truth.Truth
import retrofit2.HttpException
import kotlin.jvm.java

@RunWith(MockitoJUnitRunner::class)
class GetPhotoUseCaseTest {

    @Mock
    private lateinit var photoRepository: PhotoRepository

    @Mock
    private lateinit var getPhotoUseCase: GetPhotoUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPhotoUseCase = GetPhotoUseCase(photoRepository)
    }

    @Test
    fun `invoke should return success`() = runTest {
        val photoDto = PhotoDto()
        `when`(photoRepository.getPhotoDetails()).thenReturn(flow { emit(Result.Success(photoDto)) })
        val state = MutableStateFlow<Result<PhotoDto>>(Result.Success(photoDto))
        getPhotoUseCase.invoke().collect()
        {
            state.value = it
        }
        Truth.assertThat(state.value is Result.Success)
    }

    @Test
    fun `invoke should return error`() = runTest {
        val errorResponse = Mockito.mock(HttpException::class.java)
        `when`(photoRepository.getPhotoDetails()).thenReturn(flow {
            emit(
                Result.Error(
                    Constants.UNKNOWN_ERROR,
                    errorResponse.code()
                )
            )
        })
        val state = MutableStateFlow<Result<PhotoDto>>(
            Result.Error(
                Constants.UNKNOWN_ERROR,
                errorResponse.code()
            )
        )
        getPhotoUseCase.invoke().collect()
        {
            state.value = it
        }
        Truth.assertThat(state.value is Result.Error).isTrue()
        Truth.assertThat(errorResponse.code()).isEqualTo((state.value as Result.Error).code)
        Truth.assertThat(Constants.UNKNOWN_ERROR).isEqualTo((state.value as Result.Error).message)
    }
}