package com.example.domain.entity

data class User(
    val username: String,
    val updated_at: String,
    val bio: String,
    val id: String,
    val instagram_username: String,
    val links: LinksX,
    val location: String,
    val portfolio_url: String,
    val total_photos: Int
)