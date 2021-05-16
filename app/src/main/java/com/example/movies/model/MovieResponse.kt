package com.example.movies.model

import java.io.Serializable

class MovieData: Serializable{
    val has_more: Boolean? = null
    val results: ArrayList<MovieResponse>? = null
}
data class MovieResponse( val id: Int = 0,
                          val title: String? = null,
                          val overview: String? = null,
                          val releaseDate: String? = null,
                          val poster_path: String? = null): Serializable {

}