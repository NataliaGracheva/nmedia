package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostCardBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        viewModel.data.observe(this) { posts ->
            binding.container.removeAllViews()
            posts.map { post ->
                PostCardBinding.inflate(layoutInflater, binding.container, true).apply {
                    author.text = post.author
                    content.text = post.content
                    published.text = post.published
                    liked.text = Helper.getShortCountView(post.liked)
                    shared.text = Helper.getShortCountView(post.shared)
                    viewed.text = Helper.getShortCountView(post.viewed)
                    likedIcon.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_favorite_border_24)
                    likedIcon.setOnClickListener {
                        viewModel.likeById(post.id)
                    }
                    sharedIcon.setOnClickListener {
                        viewModel.shareById(post.id)
                    }
                }.root
            }
        }
    }
}
