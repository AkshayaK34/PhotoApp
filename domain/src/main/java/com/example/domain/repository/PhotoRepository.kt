package com.example.domain.repository

import com.example.domain.entity.PhotoDto
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun getPhotoDetails(): Flow<Result<PhotoDto>>
}