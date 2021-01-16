package com.liceadev.architectcoders.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liceadev.architectcoders.databinding.ActivityDetailBinding
import com.liceadev.architectcoders.extensions.loadPhoto
import com.liceadev.architectcoders.model.Photo

class DetailActivity : AppCompatActivity(), DetailPresenter.View {
    private lateinit var binding: ActivityDetailBinding
    private val presenter by lazy { DetailPresenter() }

    companion object {
        const val EXTRA_MOVIE = "DetailActivity:photo"
        fun getIntent(context: Context, photo: Photo): Intent {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(EXTRA_MOVIE, photo)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate(this, intent.getParcelableExtra(EXTRA_MOVIE))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


    override fun showPhoto(photo: Photo) = with(photo) {
        val background = photo.urls?.full ?: ""
        binding.ivPhotoDetail.loadPhoto(background)
        binding.tbPhotoDetail.title = photo.id
        binding.tvSummaryDetail.text = photo.description?: ""
        binding.tvInfoDetail.setPhoto(photo.user)
    }
}