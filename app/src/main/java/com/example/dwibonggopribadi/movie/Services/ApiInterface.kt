package com.example.dwibonggopribadi.movie.Services

import com.example.dwibonggopribadi.movie.Model.Movie
import com.example.dwibonggopribadi.movie.Model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("movie/latest")
    fun getMovieLatest(@Query("api_key") apiKey : String) : Call<Movie>
    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key") apiKey: String) : Call<MovieResponse>
    @GET("movie/now_playing")
    fun getNowPlaying(@Query("api_key") apiKey: String) : Call<MovieResponse>
    @GET("search/movie")
    fun searchMovies(@QueryMap options : Map<String, String>) : Call<MovieResponse>
    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") id : String, @Query("api_key") apiKey: String) : Call<Movie>

}