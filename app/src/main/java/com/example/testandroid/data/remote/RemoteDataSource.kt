package com.example.testandroid.data.remote

import com.example.testandroid.utils.Const
import com.example.testandroid.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiServices: ApiService) : BaseDataSource() {
    suspend fun getPopularMovies() = getResult { apiServices.getPopularMovies(Const.API_KEY) }
    suspend fun getTopRatingMovies() = getResult { apiServices.getTopRatingMovies(Const.API_KEY) }
    suspend fun getLatestMovies() = getResult { apiServices.getLatestMovies(Const.API_KEY) }
}