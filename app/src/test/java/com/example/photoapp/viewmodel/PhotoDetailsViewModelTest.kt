package com.example.photoapp.viewmodel

import com.example.domain.entity.Links
import com.example.domain.entity.LinksX
import com.example.domain.entity.PhotoDto
import com.example.domain.entity.PhotoDtoItem
import com.example.domain.entity.Urls
import com.example.domain.entity.User
import com.example.domain.repository.PhotoRepository
import com.example.domain.usecase.GetPhotoUseCase
import com.example.domain.utils.DispatcherProvider
import com.example.domain.utils.Resource
import com.example.domain.utils.Result
import com.example.photoapp.TestDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoDetailsViewModelTest {
    @MockK
    private lateinit var photoRepository: PhotoRepository

    @MockK
    private lateinit var getPhotoUseCase: GetPhotoUseCase

    @MockK
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewModel: PhotoDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkConstructor(Regex::class, CoroutineExceptionHandler::class, Throwable::class)
        dispatcherProvider = TestDispatcherProvider()
        getPhotoUseCase = spyk(GetPhotoUseCase(photoRepository))
        viewModel = spyk(PhotoDetailsViewModel(getPhotoUseCase, dispatcherProvider))
    }

    @Test
    fun getListOfPhotoReturnSuccess() = runTest {
        val photoItem1 = PhotoDtoItem(
            alt_description = "",
            description = "",
            id = "1",
            likes = 100,
            updated_at = "",
            links = Links(download_location = ""),
            urls = Urls(full = "", regular = ""),
            user = User(
                username = "",
                updated_at = "",
                bio = "",
                id = "",
                instagram_username = "",
                links = LinksX(html = "", likes = "", photos = "", portfolio = "", self = ""),
                location = "",
                portfolio_url = "",
                total_photos = 0
            )
        )
        val photoItem2 = PhotoDtoItem(
            alt_description = "",
            description = "",
            id = "2",
            likes = 100,
            updated_at = "",
            links = Links(download_location = ""),
            urls = Urls(full = "", regular = ""),
            user = User(
                username = "",
                updated_at = "",
                bio = "",
                id = "",
                instagram_username = "",
                links = LinksX(html = "", likes = "", photos = "", portfolio = "", self = ""),
                location = "",
                portfolio_url = "",
                total_photos = 0
            )
        )
        val mockphotoDto = PhotoDto().apply {
            add(photoItem1)
            add(photoItem2)
        }
        coEvery { getPhotoUseCase.invoke() } returns flow {
            emit(Result.Success(mockphotoDto))
        }
        viewModel.getListOfPhoto()
        val state = viewModel.state.value
        assert(state is Resource.Success && state.data == mockphotoDto)
    }

    @Test
    fun getListOfPhotoReturnLoading() {
        runTest {
            coEvery { getPhotoUseCase.invoke() } returns flow { emit(Result.Loading) }
            viewModel.getListOfPhoto()
            Assert.assertTrue(viewModel.state.value is Resource.Loading)
        }
    }

    @Test
    fun getListOfPhotoReturnError() {
        runTest {
            coEvery { getPhotoUseCase.invoke() } returns flow {
                emit(
                    Result.Error(
                        "",
                        0
                    )
                )
            }
            viewModel.getListOfPhoto()
            Assert.assertTrue(viewModel.state.value is Resource.Error)
        }
    }

    @Test
    fun getListOfPhotoReturnException() {
        val exception = Mockito.mock(HttpException::class.java)
        runTest {
            coEvery { getPhotoUseCase.invoke() } returns flow {
                emit(
                    Result.Error(
                        exception.message(),
                        exception.code()
                    )
                )
            }
            viewModel.getListOfPhoto()
            Assert.assertTrue(viewModel.state.value is Resource.Error)
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}