package com.example.movies.adapter

import androidx.paging.PageKeyedDataSource
import com.example.movies.model.MovieResponse
import com.example.movies.util.RetrofitProvider
import android.util.Log;
import com.example.movies.model.MovieData

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

const val PAGE_SIZE = 30
class MovieDataSource : PageKeyedDataSource<Int, MovieResponse>() {
    private val FIRST_PAGE = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieResponse>) {
        RetrofitProvider().getClient.callPagedApi(FIRST_PAGE).enqueue(object : Callback<MovieData> {
            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                if (response.body() != null) {
                    response.body().results?.let { callback.onResult(it, null, FIRST_PAGE + 1) }
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable?) {
                Log.e("","Api Failed ${t?.printStackTrace()}")
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResponse>) {
        RetrofitProvider().getClient.callPagedApi(params.key).enqueue(object : Callback<MovieData> {

            override fun onFailure(call: Call<MovieData>?, t: Throwable?) {
                Log.e("","Api Failed ${t?.printStackTrace()}")
            }

            override fun onResponse(call: Call<MovieData>?, response: Response<MovieData>?) {
                if (response?.body() != null) {
                    val key: Int? = params.key + 1
                    callback.onResult(response.body().results!!, key);
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResponse>) {
        RetrofitProvider().getClient.callPagedApi(params.key).enqueue(object : Callback<MovieData> {

            override fun onFailure(call: Call<MovieData>?, t: Throwable?) {
                Log.e("","Api Failed ${t?.printStackTrace()}")
            }

            override fun onResponse(call: Call<MovieData>?, response: Response<MovieData>?) {
                val adjacentKey: Int? = if (params.key > 1){
                    params.key - 1
                } else{
                    null
                }
                if (response?.body() != null) {
                    response.body().results?.let { callback.onResult(it, adjacentKey) };
                }
            }
        })
    }

}