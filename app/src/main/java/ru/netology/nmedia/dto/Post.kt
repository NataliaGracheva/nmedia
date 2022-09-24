package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val liked: Long,
    val shared: Long,
    val viewed: Long,
    val likedByMe: Boolean = false,
    val video: String? = null
)
