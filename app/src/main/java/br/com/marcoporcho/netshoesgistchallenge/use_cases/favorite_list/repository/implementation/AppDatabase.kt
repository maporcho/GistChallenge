package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.implementation

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist

@Database(entities = arrayOf(FavoriteGist::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteGistDao(): FavoriteGistDao
}