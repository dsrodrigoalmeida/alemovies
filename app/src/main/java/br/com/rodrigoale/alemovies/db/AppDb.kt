package br.com.rodrigoale.alemovies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rodrigoale.alemovies.dao.MovieDAO
import br.com.rodrigoale.alemovies.model.Movie

@Database (entities = [Movie::class],version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun movieDao(): MovieDAO
}