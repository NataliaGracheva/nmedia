package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var liked: Long,
    var shared: Long,
    val viewed: Long,
    var likedByMe: Boolean = false
)
