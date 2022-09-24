package ru.netology.nmedia.activity

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.utils.Helper
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post


class PostViewHolder(
    private val binding: PostCardBinding,
    private val listener: OnInteractionsListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likedIcon.isChecked = post.likedByMe
            likedIcon.text = Helper.getShortCountView(post.liked)
            sharedIcon.text = Helper.getShortCountView(post.shared)
            viewedIcon.text = Helper.getShortCountView(post.viewed)
            likedIcon.setOnClickListener {
                listener.onLike(post)
            }
            sharedIcon.setOnClickListener {
                listener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }


}