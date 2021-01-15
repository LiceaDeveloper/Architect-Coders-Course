package com.liceadev.mymovies.ui.main

import android.os.Bundle
import android.view.View
import com.liceadev.mymovies.databinding.ActivityMainBinding
import com.liceadev.mymovies.model.Movie
import com.liceadev.mymovies.model.MoviesRepository
import com.liceadev.mymovies.ui.common.CoroutineScopeActivity
import com.liceadev.mymovies.ui.detail.DetailActivity

class MainActivity : CoroutineScopeActivity(), MainPresenter.View {
    private val presenter by lazy {   MainPresenter(MoviesRepository(this))}
    private lateinit var binding: ActivityMainBinding

    private var mMoviesAdapter: MoviesAdapter = MoviesAdapter { movie ->
        presenter.onMovieClick(movie)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate(this)
        binding.rvMovies.adapter = mMoviesAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun loadMovies(movies: List<Movie>) {
        mMoviesAdapter.movies = movies
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun navigateToMovie(movie: Movie) {
        startActivity(DetailActivity.getIntent(this, movie))
    }
}