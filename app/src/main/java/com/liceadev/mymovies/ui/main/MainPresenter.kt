package com.liceadev.mymovies.ui.main

import com.liceadev.mymovies.model.Movie
import com.liceadev.mymovies.model.MoviesRepository
import com.liceadev.mymovies.model.Scope
import kotlinx.coroutines.launch

class MainPresenter(private val moviesRepository: MoviesRepository) : Scope by Scope.ScopeImpl() {
    interface View {
        fun showProgress()
        fun loadMovies(movies: List<Movie>)
        fun hideProgress()
        fun navigateToMovie(movie: Movie)
    }

    private var mView: View? = null

    fun onCreate(view: View) {
        initScope()
        mView = view
        launch {
            mView?.apply {
                showProgress()
                loadMovies(moviesRepository.findPopularMoviesByRegion().results)
                hideProgress()
            }
        }
    }

    fun onDestroy() {
        cancelScope()
        mView = null
    }

    fun onMovieClick(movie: Movie) {
        mView?.navigateToMovie(movie)
    }
}