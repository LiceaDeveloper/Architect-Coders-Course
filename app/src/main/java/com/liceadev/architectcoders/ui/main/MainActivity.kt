package com.liceadev.architectcoders.ui.main

import android.os.Bundle
import android.view.View
import com.liceadev.architectcoders.databinding.ActivityMainBinding
import com.liceadev.architectcoders.model.PhotosRepository
import com.liceadev.architectcoders.model.Photo
import com.liceadev.architectcoders.ui.common.CoroutineScopeActivity
import com.liceadev.architectcoders.ui.detail.DetailActivity

class MainActivity : CoroutineScopeActivity(), MainPresenter.View {
    private val presenter by lazy {   MainPresenter(PhotosRepository(this))}
    private lateinit var binding: ActivityMainBinding

    private var mPhotosAdapter: PhotosAdapter = PhotosAdapter { photo ->
        presenter.onPhotoClick(photo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate(this)
        binding.rvPhotos.adapter = mPhotosAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun loadPhotos(photos: List<Photo>) {
        mPhotosAdapter.photos = photos
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun navigateToPhoto(photo: Photo) {
        startActivity(DetailActivity.getIntent(this, photo))
    }
}