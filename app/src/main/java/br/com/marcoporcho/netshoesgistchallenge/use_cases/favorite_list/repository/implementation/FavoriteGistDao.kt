package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.implementation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist

@Dao
interface FavoriteGistDao {

    @Query("SELECT * FROM favorite_gists")
    fun getAll(): List<FavoriteGist>

    @Query("SELECT * FROM favorite_gists WHERE id = :favoriteGistId")
    fun get(favoriteGistId: String): FavoriteGist?

    @Insert
    fun insertAll(vararg gists: FavoriteGist)

    @Delete
    fun delete(gist: FavoriteGist)

    @Query("DELETE FROM favorite_gists WHERE id = :favoriteGistId")
    fun deleteById(favoriteGistId: String)

    @Query("SELECT EXISTS (SELECT * FROM favorite_gists WHERE id = :id)")
    fun exists(id: String): Boolean
}