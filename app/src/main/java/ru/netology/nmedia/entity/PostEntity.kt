package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val date: String,
    val liked: String,
    val shared: Long,
    val viewed: Long,
    val likedByMe: Boolean = false,
    val video: String? = null
) {
    fun toDto() = Post(
        id = id,
        author = author,
        content = content,
        published = date,
        liked = liked.toLong(),
        shared = shared,
        viewed = viewed,
        likedByMe = likedByMe,
        video = video
    )

    companion object {
        fun fromDto(post: Post) = PostEntity(
            id = post.id,
            author = post.author,
            content = post.content,
            date = post.published,
            liked = post.liked.toString(),
            shared = post.shared,
            viewed = post.viewed,
            likedByMe = post.likedByMe,
            video = post.video
        )
    }
}