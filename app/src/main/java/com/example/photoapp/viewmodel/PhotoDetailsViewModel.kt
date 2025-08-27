package com.example.photoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.PhotoDto
import com.example.domain.usecase.GetPhotoUseCase
import com.example.domain.utils.DispatcherProvider
import com.example.domain.utils.Resource
import com.example.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<PhotoDto>>(Resource.Loading())
    val state: StateFlow<Resource<PhotoDto>> get() = _state.asStateFlow()

    fun getListOfFood() {
        viewModelScope.launch(dispatcherProvider.main) {
            getPhotoUseCase.invoke()
                .flowOn(dispatcherProvider.io)
                .catch { _state.value = Resource.Error(it.message.toString()) }
                .collect { response ->
                    Log.d("response", response.toString())
                    when (response) {
                        is Result.Loading -> _state.value = Resource.Loading()
                        is Result.Error -> _state.value = Resource.Error(response.message)
                        is Result.Success -> _state.value = Resource.Success(response.data)
                    }
                }

        }
    }


}