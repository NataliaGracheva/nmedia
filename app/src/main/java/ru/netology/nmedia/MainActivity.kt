package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var post: Post
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initViews()
        setListeners()
    }

    private fun initViews() {
        binding.apply {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            liked.text = Helper.getShortCountView(post.liked)
            shared.text = Helper.getShortCountView(post.shared)
            viewed.text = Helper.getShortCountView(post.viewed)
            if (post.likedByMe) likedIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }

    private fun initData() {
        post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            liked = 999,
            shared = 10999,
            viewed = 1100000
        )
    }

    private fun setListeners() {
        binding.apply {
            likedIcon.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likedIcon.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_favorite_border_24
                )
                if (post.likedByMe) post.liked++ else post.liked--
                liked.text = Helper.getShortCountView(post.liked)
            }
            sharedIcon.setOnClickListener {
                post.shared++
                shared.text = Helper.getShortCountView(post.shared)
            }
        }
    }
}