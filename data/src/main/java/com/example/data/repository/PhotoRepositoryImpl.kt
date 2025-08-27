package com.example.data.repository

import com.example.data.remote.ApiService
import com.example.data.utils.NetworkBoundResource
import com.example.domain.entity.PhotoDto
import com.example.domain.repository.PhotoRepository
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkBoundResource: NetworkBoundResource
) : PhotoRepository {
    override suspend fun getPhotoDetails(): Flow<Result<PhotoDto>> {
        return networkBoundResource.downloadData { apiService.getPhotoDetails() }
    }
}