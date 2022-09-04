package ru.netology.nmedia.activity

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Helper
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post

typealias OnLikeListener = (Post) -> Unit
typealias OnShareListener = (Post) -> Unit

class PostViewHolder(
    private val binding: PostCardBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            liked.text = Helper.getShortCountView(post.liked)
            shared.text = Helper.getShortCountView(post.shared)
            viewed.text = Helper.getShortCountView(post.viewed)
            likedIcon.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_favorite_border_24)
            likedIcon.setOnClickListener {
                onLikeListener(post)
            }
            sharedIcon.setOnClickListener {
                onShareListener(post)
            }
        }
    }


}