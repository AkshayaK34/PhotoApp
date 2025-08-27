package com.example.data.di

import com.example.data.repository.PhotoRepositoryImpl
import com.example.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun binLBGRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository
}