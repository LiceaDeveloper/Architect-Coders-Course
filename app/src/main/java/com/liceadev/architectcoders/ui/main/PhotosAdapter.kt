package com.liceadev.architectcoders.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liceadev.architectcoders.databinding.ItemPhotoBinding
import com.liceadev.architectcoders.extensions.loadPhoto
import com.liceadev.architectcoders.model.Photo
import kotlin.properties.Delegates

class PhotosAdapter(private val photoClick: (Photo) -> Unit) :
    RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    var photos: List<Photo> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
        holder.itemView.setOnClickListener { photoClick.invoke(photo) }
    }

    override fun getItemCount(): Int = photos.size

    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.tvPhotoName.text =
                if (photo.description != null || photo.altDescription != null) {
                    photo.description ?: photo.altDescription
                } else {
                    ""
                }
            binding.ivPhoto.loadPhoto(photo.urls?.thumb ?: "")
        }
    }
}