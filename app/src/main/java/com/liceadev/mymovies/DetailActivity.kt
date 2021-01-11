package com.liceadev.mymovies

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.liceadev.mymovies.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TITLE = "DetailActivity:title"
        fun getIntent(context: Context, title: String): Intent {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra(EXTRA_TITLE, title)
            return i
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(EXTRA_TITLE)
        binding.tvTitle.text = title
    }
}