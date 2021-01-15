package com.liceadev.mymovies.ui.detail

import com.liceadev.mymovies.model.Movie

class DetailPresenter {
    interface View {
        fun showMovie(movie: Movie)
    }

    private var mView: View? = null

    fun onCreate(view: View, movies: Movie) {
        mView = view
        mView?.showMovie(movies)

    }

    fun onDestroy() {
        mView = null
    }
}