package com.liceadev.architectcoders.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.liceadev.architectcoders.R
import com.liceadev.architectcoders.databinding.ActivityDetailBinding
import com.liceadev.architectcoders.extensions.app
import com.liceadev.architectcoders.extensions.getViewModel
import com.liceadev.architectcoders.extensions.loadPhoto
import com.liceadev.architectcoders.model.server.PhotosRepository

class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_PHOTO_ID = "DetailActivity:photoId"
        fun getIntent(context: Context, photoId: Int): Intent {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(EXTRA_PHOTO_ID, photoId)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel {
            DetailViewModel(
                intent.getIntExtra(EXTRA_PHOTO_ID, -1),
                PhotosRepository(app)
            )
        }
        viewModel.model.observe(this, Observer(::updateUi))

        binding.fabFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun updateUi(model: DetailViewModel.UiModel) {
        val photo = model.photo
        val background = photo.urlFull
        binding.ivPhotoDetail.loadPhoto(background)
        binding.tbPhotoDetail.title = photo.description
        val likes = photo.likes
        binding.tvLikes.text = getString(R.string.detail_likes_count, likes)

//        binding.tvInfoDetail.setPhoto(photo.user)
    }
}