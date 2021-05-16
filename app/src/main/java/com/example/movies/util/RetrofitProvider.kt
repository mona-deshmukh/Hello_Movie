package com.example.movies.util

import com.example.movies.service.LoadMoviesService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private var retrofit: Retrofit? = null


    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getClient: LoadMoviesService
        get() {

            val gson = GsonBuilder()
                .setLenient()
                .create()
            val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(LoadMoviesService::class.java)

        }

  /*  fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun getApi(): LoadMoviesService? {
        return retrofit?.create(LoadMoviesService::class.java)
    }*/
}