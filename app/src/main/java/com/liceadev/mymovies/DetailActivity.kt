package com.liceadev.mymovies

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.liceadev.mymovies.databinding.ActivityDetailBinding
import com.liceadev.mymovies.extensions.loadMovie
import com.liceadev.mymovies.model.Movie

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE = "DetailActivity:movie"
        fun getIntent(context: Context, movie: Movie): Intent {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(EXTRA_MOVIE,movie)
            return i
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        binding.tvTitle.text = movie?.title
        binding.ivPoster.loadMovie("https://image.tmdb.org/t/p/w185/${movie?.posterPath}")
    }
}