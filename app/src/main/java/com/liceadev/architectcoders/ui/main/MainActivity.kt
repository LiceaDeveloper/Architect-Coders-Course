package com.liceadev.architectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.liceadev.architectcoders.databinding.ActivityMainBinding
import com.liceadev.architectcoders.extensions.getViewModel
import com.liceadev.architectcoders.model.PermissionRequester
import com.liceadev.architectcoders.model.PhotosRepository
import com.liceadev.architectcoders.ui.common.CoroutineScopeActivity
import com.liceadev.architectcoders.ui.detail.DetailActivity
import com.liceadev.architectcoders.ui.main.MainViewModel.UiModel

class MainActivity : CoroutineScopeActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PhotosAdapter

    private val permissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel { MainViewModel(PhotosRepository(application)) }
        viewModel.model.observe(this, Observer(::updateUi))

        adapter = PhotosAdapter(viewModel::onPhotoClick)
        binding.rvPhotos.adapter = adapter
    }

    private fun updateUi(model: UiModel) {
        binding.progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is UiModel.Content -> adapter.photos = model.photos
            is UiModel.Navigation -> startActivity(DetailActivity.getIntent(this, model.photo))
            is UiModel.RequestLocationPermission -> permissionRequester.request {
                viewModel.onPermissionRequested()
            }
        }
    }
}