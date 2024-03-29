package com.example.testandroid.data.remote

import com.example.testandroid.data.model.GetMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Response<GetMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatingMovies(@Query("api_key") apiKey: String): Response<GetMoviesResponse>

    @GET("movie/now_playing")
    suspend fun getLatestMovies(@Query("api_key") apiKey: String): Response<GetMoviesResponse>
}