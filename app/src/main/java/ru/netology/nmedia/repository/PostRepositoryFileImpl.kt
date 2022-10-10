package ru.netology.nmedia.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryFileImpl(private val context: Context) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            sync()
        }
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    override fun get(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        Log.d("Listener", "click on like")
        posts = posts.map { post ->
            if (post.id == id) post.copy(
                liked = if (post.likedByMe) post.liked - 1 else post.liked + 1,
                likedByMe = !post.likedByMe
            ) else post
        }
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {
        Log.d("Listener", "click on share")
        posts = posts.map { post ->
            if (post.id == id) post.copy(shared = post.shared + 1)
            else post
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = (posts.firstOrNull()?.id ?: 0L) + 1,
                    author = "Нетология. Университет интернет-профессий будущего",
                    published = "now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }
        posts = posts.map { if (it.id != post.id) it else post.copy(content = post.content) }
        data.value = posts
        sync()
    }
}