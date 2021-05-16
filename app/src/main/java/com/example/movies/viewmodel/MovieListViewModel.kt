package com.example.movies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.movies.adapter.MovieDataSourceFactory
import com.example.movies.adapter.PAGE_SIZE
import com.example.movies.model.MovieResponse
import com.example.movies.util.RetrofitProvider
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class MovieListViewModel : ViewModel(), CoroutineScope {
    val sJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + sJob

    private var _error: MutableLiveData<Boolean>? = null
    private var _loading: MutableLiveData<Boolean>? = null
    private var _popularMovies: MutableLiveData<ArrayList<MovieResponse>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, MovieResponse>>? = null
    var itemPagedList: LiveData<PagedList<MovieResponse>>? = null

    fun loadAllMovies() {
        val itemDataSourceFactory = MovieDataSourceFactory()
        liveDataSource = itemDataSourceFactory.itemLiveDataSource

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, pagedListConfig).build()
    }

    val error: MutableLiveData<Boolean>
        get() {
            if (_error == null) {
                _error = MutableLiveData()
            }
            return _error!!
        }

    val loading: MutableLiveData<Boolean>
        get() {
            if (_loading == null) {
                _loading = MutableLiveData()
            }
            return _loading!!
        }
    val popularMovies: MutableLiveData<ArrayList<MovieResponse>>
        get() {
            if (_popularMovies == null) {
                _popularMovies = MutableLiveData()
            }
            return _popularMovies!!
        }

    fun loadPopularMovies() {
        _loading?.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitProvider().getClient.callPopularMovieApi()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        _loading?.postValue(false)
                        _popularMovies?.postValue(response.body().results)
                    } else {
                        _loading?.postValue(false)
                        _error?.postValue(true)
                    }
                } catch (e: Exception) {
                    _loading?.postValue(false)
                    _error?.postValue(true)
                }
            }
        }
    }

    override fun onCleared() {
        sJob.cancel()
        super.onCleared()
    }
}
