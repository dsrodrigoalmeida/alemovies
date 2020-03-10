package br.com.rodrigoale.alemovies.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.rodrigoale.alemovies.model.Movie

@Dao
interface MovieDAO
{
    @Insert
    fun saveMovie(movie: Movie)

    @Query(value = "Select * from movie where user IN(:user)")
    fun getMovie(user : String) : List<Movie>


}