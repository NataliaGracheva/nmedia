package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setListeners()
    }

    private fun initViews() {
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                content.text = post.content
                published.text = post.published
                liked.text = Helper.getShortCountView(post.liked)
                shared.text = Helper.getShortCountView(post.shared)
                viewed.text = Helper.getShortCountView(post.viewed)
                likedIcon.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_favorite_border_24)
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            likedIcon.setOnClickListener {
                viewModel.like()
            }
            sharedIcon.setOnClickListener {
                viewModel.share()
            }
        }
    }
}