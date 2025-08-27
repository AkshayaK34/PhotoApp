package com.example.data.remote

import com.example.domain.entity.PhotoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("photos")
    suspend fun getPhotoDetails(
        @Query("query") search : String = "food"
    ): Response<PhotoDto>
}