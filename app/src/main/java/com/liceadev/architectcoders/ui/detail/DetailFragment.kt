package com.liceadev.architectcoders.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.liceadev.architectcoders.R
import com.liceadev.architectcoders.databinding.FragmentDetailBinding
import com.liceadev.architectcoders.extensions.loadPhoto
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : ScopeFragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel
            by viewModel{
                val photoId = arguments?.getInt("id", -1) ?: -1
                parametersOf(photoId)
            }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))

        binding.fabFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
    }


    private fun updateUi(model: DetailViewModel.UiModel) {
        val photo = model.photo
        val background = photo.urlFull
        binding.ivPhotoDetail.loadPhoto(background)
        binding.tbPhotoDetail.title = photo.description
        val likes = photo.likes
        binding.tvLikes.text = getString(R.string.detail_likes_count, likes)

        binding.tvInfoDetail.setPhoto(photo.user)

        val icon = if (photo.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), icon))
    }
}