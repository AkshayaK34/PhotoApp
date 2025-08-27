package com.example.domain.entity

data class PhotoDtoItem(
    val alt_description: String,
    val description: String,
    val id: String,
    val likes: Int,
    val links: Links,
    val updated_at: String,
    val urls: Urls,
    val user: User
)