package ru.netology.nmedia.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.PostCardBinding
import ru.netology.nmedia.dto.Post

interface OnInteractionsListener {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onPlay(post: Post)
}

class PostAdapter(
    private val listener: OnInteractionsListener,
    private val onPostClickListener: OnPostClickListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.itemView.setOnClickListener {
            onPostClickListener.onPostClick(post)
        }
        holder.bind(post)
    }

    class OnPostClickListener(val clickListener: (post: Post) -> Unit) {
        fun onPostClick(post: Post) = clickListener(post)
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

}