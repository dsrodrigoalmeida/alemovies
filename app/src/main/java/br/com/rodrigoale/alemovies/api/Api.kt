package br.com.rodrigoale.alemovies.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "6b819bab1750c270282b6c3dc637fac9",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>


}