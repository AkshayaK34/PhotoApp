package com.example.domain.usecase

import com.example.domain.entity.PhotoDto
import com.example.domain.repository.PhotoRepository
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoodUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(): Flow<Result<PhotoDto>> {
        return photoRepository.getPhotoDetails()
    }
}


