package com.example.movies.view

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.R
import com.example.movies.model.MovieResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.vector_back)

        init()
    }

    private fun init(){
        val movie = intent.getSerializableExtra(INTENT_MOVIE) as MovieResponse
        Picasso.get().load("https://image.tmdb.org/t/p/w185${movie.poster_path}").error(R.drawable.movie_placeholder).into(imageView)

        supportActionBar?.title = movie.title
        textExplanation.text = movie.overview
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}