package com.liceadev.mymovies.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.liceadev.mymovies.databinding.ActivityDetailBinding
import com.liceadev.mymovies.extensions.loadMovie
import com.liceadev.mymovies.model.Movie

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE = "DetailActivity:movie"
        fun getIntent(context: Context, movie: Movie): Intent {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(EXTRA_MOVIE, movie)
            return i
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (movie != null) {
            val background = movie.backdropPath ?: movie.posterPath
            binding.ivMovieDetail.loadMovie("https://image.tmdb.org/t/p/w185/${background}")

            binding.tbMovieDetail.title = movie.title
            binding.tvSummaryDetail.text = movie.overview
            bindDetailInfo(binding.tvInfoDetail, movie)
        }

    }

    private fun bindDetailInfo(tvInfoDetail: TextView, movie: Movie) {
        tvInfoDetail.text = buildSpannedString {

            bold { append("Original language: ") }
            appendLine(movie.originalLanguage)

            bold { append("Original title: ") }
            appendLine(movie.originalTitle)

            bold { append("Release date: ") }
            appendLine(movie.releaseDate)

            bold { append("Popularity: ") }
            appendLine(movie.popularity.toString())

            bold { append("Vote Average: ") }
            append(movie.voteAverage.toString())
        }
    }
}