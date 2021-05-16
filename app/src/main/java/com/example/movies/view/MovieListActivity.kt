package com.example.movies.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.viewmodel.MovieListViewModel
import com.example.movies.R
import com.example.movies.adapter.MovieListAdapter
import kotlinx.android.synthetic.main.activity_movie_list.*

const val INTENT_MOVIE = "movie"

class MovieListActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieListViewModel
    private lateinit var popularMoviesAdapter: MovieListAdapter
    private lateinit var allMoviesAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        viewModel.loadAllMovies()

        popularMoviesAdapter = MovieListAdapter(true)
        allMoviesAdapter = MovieListAdapter(false)

        popularMoviesAdapter.onItemClick = {
            val intent = Intent(this@MovieListActivity, MovieDetailActivity::class.java).apply {
                putExtra(INTENT_MOVIE, it)
            }
            startActivity(intent)
            Toast.makeText(this, "${it.title}", Toast.LENGTH_LONG).show()
        }

        allMoviesAdapter.onItemClick = {
            val intent = Intent(this@MovieListActivity, MovieDetailActivity::class.java).apply {
                putExtra(INTENT_MOVIE, it)
            }
            startActivity(intent)
            Toast.makeText(this, "${it.title}", Toast.LENGTH_LONG).show()
        }


        viewModel.loading.observe(this, Observer {
            if (it == true) {
                /*  progressView.start()
                  progressView.visibility = View.VISIBLE*/
            } else {
                /*   progressView.visibility = View.GONE
                   progressView.stop()*/
            }
        })

        viewModel.error.observe(this, Observer {
            if (it == true) {
                Log.e("Error", "")
                /*   progressView.visibility = View.GONE
                progressView.stop()*/
            }
        })

        viewModel.itemPagedList?.observe(this, Observer {
            if (it != null) {
                popularMoviesAdapter.submitList(it)
                allMoviesAdapter.submitList(it)
            }
        })

        setPopularList()
        setAllList()
    }

    private fun setAllList() {
        val layoutManager = GridLayoutManager(this, 3)
        rvAllMovies.layoutManager = layoutManager

        rvAllMovies.adapter = allMoviesAdapter
        allMoviesAdapter.notifyDataSetChanged()
    }

    private fun setPopularList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPopularMovies.layoutManager = layoutManager

        rvPopularMovies.adapter = popularMoviesAdapter
        popularMoviesAdapter.notifyDataSetChanged()
    }
}