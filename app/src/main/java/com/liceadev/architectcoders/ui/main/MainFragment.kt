package com.liceadev.architectcoders.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.liceadev.architectcoders.R
import com.liceadev.architectcoders.data.PermissionRequester
import com.liceadev.architectcoders.databinding.FragmentMainBinding
import com.liceadev.architectcoders.ui.common.EventObserver
import com.liceadev.architectcoders.ui.main.MainViewModel.UiModel
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : ScopeFragment() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: PhotosAdapter
    private lateinit var navController: NavController

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
        navController = view.findNavController()

        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver { photoId ->
            navController.navigate(
                R.id.action_mainFragment_to_detailFragment,
                bundleOf("id" to photoId)
            )
        })

        adapter = PhotosAdapter(viewModel::onPhotoClick)
        binding.rvPhotos.adapter = adapter
    }

    private fun updateUi(model: UiModel) {
        binding.progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is UiModel.Content -> adapter.photos = model.photos
            is UiModel.RequestLocationPermission -> permissionRequester.request {
                viewModel.onPermissionRequested()
            }
        }
    }
}