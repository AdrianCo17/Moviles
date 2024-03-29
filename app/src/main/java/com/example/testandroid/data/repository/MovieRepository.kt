package com.example.testandroid.data.repository

import com.example.testandroid.data.local.MovieDao
import com.example.testandroid.data.model.MovieType
import com.example.testandroid.data.model.toMovieEntityList
import com.example.testandroid.data.remote.RemoteDataSource
import javax.inject.Inject
import com.example.testandroid.utils.performGetOperation

class MovieRepository @Inject constructor(
    private val localDataSource: MovieDao,
    private val remoteDataSource: RemoteDataSource) {


    fun getPopularMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies(MovieType.POPULAR.value) },
        networkCall = { remoteDataSource.getPopularMovies() },
        saveCallResult = { localDataSource.insertAll(it.results.toMovieEntityList(MovieType.POPULAR.value)) }
    )

    fun getTopRatingMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies(MovieType.TOPRATING.value) },
        networkCall = { remoteDataSource.getTopRatingMovies() },
        saveCallResult = { localDataSource.insertAll(it.results.toMovieEntityList(MovieType.TOPRATING.value)) }
    )

    fun getLatestMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies(MovieType.LATEST.value) },
        networkCall = { remoteDataSource.getLatestMovies() },
        saveCallResult = { localDataSource.insertAll(it.results.toMovieEntityList(MovieType.LATEST.value)) }
    )

}