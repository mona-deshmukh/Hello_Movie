package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.model.MovieResponse
import com.squareup.picasso.Picasso


class MovieListAdapter(private val isPopularMovieLayout: Boolean) : PagedListAdapter<MovieResponse, MovieListAdapter.ItemViewHolder>(DIFF_CALLBACK) {
    var onItemClick: ((MovieResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val gg: Int = if (isPopularMovieLayout){
            R.layout.popular_movie_list_item
        }else{
            R.layout.all_movies_list_item
        }
        val view = LayoutInflater.from(parent.context).inflate(gg, parent, false)
        val holder = ItemViewHolder(view)
        holder.container.setOnClickListener { getItem(holder.adapterPosition)?.let { it1 ->
            onItemClick?.invoke(
                it1
            )
        } }

        return holder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (!getItem(position)?.poster_path.isNullOrBlank())
            Picasso.get().load("https://image.tmdb.org/t/p/w185${getItem(position)?.poster_path}")
                .placeholder(R.drawable.movie_placeholder)
                .into(holder.movieImage)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: CardView = itemView.findViewById(R.id.container)
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieResponse> =
            object : DiffUtil.ItemCallback<MovieResponse>() {
                override fun areItemsTheSame(
                    oldItem: MovieResponse,
                    newItem: MovieResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: MovieResponse,
                    newItem: MovieResponse
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
