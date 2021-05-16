package com.example.movies.service

import com.example.movies.model.MovieData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoadMoviesService {

    @GET("movie/popular?api_key=c161a503aa723bc381984697d4a965e7")
    fun callPagedApi(@Query("page") page: Int): Call<MovieData>

    @GET("movie/popular?api_key=c161a503aa723bc381984697d4a965e7")
    suspend fun callPopularMovieApi(): Response<MovieData>
}