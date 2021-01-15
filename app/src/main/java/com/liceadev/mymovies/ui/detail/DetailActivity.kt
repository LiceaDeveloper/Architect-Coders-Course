package com.liceadev.mymovies.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liceadev.mymovies.databinding.ActivityDetailBinding
import com.liceadev.mymovies.extensions.loadMovie
import com.liceadev.mymovies.model.Movie

class DetailActivity : AppCompatActivity(), DetailPresenter.View {
    private lateinit var binding: ActivityDetailBinding
    private val presenter by lazy { DetailPresenter() }

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
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate(this, intent.getParcelableExtra(EXTRA_MOVIE))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showMovie(movie: Movie) = with(movie) {
        val background = movie.backdropPath ?: movie.posterPath
        binding.ivMovieDetail.loadMovie("https://image.tmdb.org/t/p/w185/${background}")
        binding.tbMovieDetail.title = movie.title
        binding.tvSummaryDetail.text = movie.overview
        binding.tvInfoDetail.setMovie(movie)
    }
}