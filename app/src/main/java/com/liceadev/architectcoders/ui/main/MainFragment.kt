package com.liceadev.architectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.liceadev.architectcoders.databinding.FragmentMainBinding
import com.liceadev.architectcoders.extensions.app
import com.liceadev.architectcoders.extensions.getViewModel
import com.liceadev.architectcoders.model.PermissionRequester
import com.liceadev.architectcoders.model.server.PhotosRepository
import com.liceadev.architectcoders.ui.main.MainViewModel.UiModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PhotosAdapter

    private val permissionRequester by lazy {
        PermissionRequester(requireActivity(), ACCESS_COARSE_LOCATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel { MainViewModel(PhotosRepository(requireContext().app)) }
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))

        adapter = PhotosAdapter(viewModel::onPhotoClick)
        binding.rvPhotos.adapter = adapter
    }

    private fun updateUi(model: UiModel) {
        binding.progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is UiModel.Content -> adapter.photos = model.photos
//            is UiModel.Navigation -> startActivity(DetailFragment.getIntent(this, model.photo.id))
            is UiModel.RequestLocationPermission -> permissionRequester.request {
                viewModel.onPermissionRequested()
            }
        }

    }
}