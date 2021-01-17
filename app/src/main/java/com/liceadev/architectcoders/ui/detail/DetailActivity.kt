package com.liceadev.architectcoders.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.liceadev.architectcoders.R
import com.liceadev.architectcoders.databinding.ActivityDetailBinding
import com.liceadev.architectcoders.extensions.getViewModel
import com.liceadev.architectcoders.extensions.loadPhoto
import com.liceadev.architectcoders.model.Photo

class DetailActivity : AppCompatActivity(){
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_PHOTO = "DetailActivity:photo"
        fun getIntent(context: Context, photo: Photo): Intent {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(EXTRA_PHOTO, photo)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photo: Photo = intent.getParcelableExtra(EXTRA_PHOTO)
            ?: throw (IllegalStateException("Photo not found"))

        viewModel = getViewModel { DetailViewModel(photo) }
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: DetailViewModel.UiModel) {
        val photo = model.photo
        val background = photo.urls?.full ?: ""
        binding.ivPhotoDetail.loadPhoto(background)
        binding.tbPhotoDetail.title =
            if (photo.description != null || photo.altDescription != null) {
                photo.description ?: photo.altDescription
            } else {
                ""
            }
        val likes = photo.likes ?: 0
        binding.tvLikes.text = getString(R.string.detail_likes_count, likes)
        binding.tvInfoDetail.setPhoto(photo.user)
    }
}