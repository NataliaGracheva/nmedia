package ru.netology.nmedia.activity

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel


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
        val adapter = PostAdapter( object :
        OnInteractionsListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "share post")
                startActivity(shareIntent)
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        val activityLauncher = registerForActivityResult(NewPostActivity.Contract) { text ->
            text ?: return@registerForActivityResult
//            viewModel.changeContent(text.toString())
//            viewModel.save()
            viewModel.changeContentAndSave(text.toString())
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
//            binding.prevContent.text = post.content
//            binding.editMode.visibility = View.VISIBLE
//            with(binding.content) {
//                requestFocus()
//                setText(post.content)
//            }
            activityLauncher.launch(post.content)
        }
//        binding.save.setOnClickListener {
//            with(binding.content) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        context,
//                        R.string.empty_content_toast,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//                binding.prevContent.text = ""
//                binding.editMode.visibility = View.GONE
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }
//        binding.cancel.setOnClickListener {
//            viewModel.cancelEditing()
//            binding.prevContent.text = ""
//            binding.editMode.visibility = View.GONE
//            with(binding.content) {
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }

        binding.add.setOnClickListener {
            activityLauncher.launch(null)
        }
    }
}
