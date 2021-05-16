package com.example.movies.adapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.movies.model.MovieResponse


class MovieDataSourceFactory : DataSource.Factory<Int, MovieResponse>() {

    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, MovieResponse>>()

    override fun create(): DataSource<Int, MovieResponse> {
        val itemDataSource = MovieDataSource()

        itemLiveDataSource.postValue(itemDataSource)

        return itemDataSource
    }
}
