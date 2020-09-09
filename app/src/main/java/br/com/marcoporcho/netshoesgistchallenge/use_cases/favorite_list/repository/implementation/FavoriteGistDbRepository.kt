package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.implementation

import android.content.Context
import androidx.room.Room
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.FavoriteGistRepository
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.converter.FavoriteGistConverter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FavoriteGistDbRepository(var context: Context, private val favoriteGistConverter: FavoriteGistConverter):
    FavoriteGistRepository {

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()

    override fun fetchAll(onSuccess: (List<FavoriteGist>) -> Unit, onFailure: (Throwable) -> Unit) {

        try {
            var gists: List<FavoriteGist> = listOf()
            runBlocking {
                withContext(Dispatchers.IO) {
                    gists = db.favoriteGistDao().getAll()
                }
            }
            onSuccess(gists)
        } catch (e: Throwable) {
            onFailure(e)
        }

    }

    override fun fetch(
        favoriteGistId: String,
        onSuccess: (FavoriteGist?) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            var gist: FavoriteGist? = null
            runBlocking {
                withContext(Dispatchers.IO) {
                    gist = db.favoriteGistDao().get(favoriteGistId)
                }
            }
            onSuccess(gist)
        } catch (e: Throwable) {
            onFailure(e)
        }
    }

    override fun insert(
        gist: Gist,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            runBlocking {
                withContext(Dispatchers.IO) {
                    val favoriteGist = favoriteGistConverter.toFavoriteGist(gist)
                    db.favoriteGistDao().insertAll(favoriteGist)
                }
            }
            onSuccess()
        } catch (e: Throwable) {
            onFailure(e)
        }
    }

    override fun delete(
        favoriteGist: FavoriteGist,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            runBlocking {
                withContext(Dispatchers.IO) {
                    db.favoriteGistDao().delete(favoriteGist)
                }
            }
            onSuccess()
        } catch (e: Throwable) {
            onFailure(e)
        }
    }

    override fun delete(
        favoriteGistId: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit) {

        try {
            runBlocking {
                withContext(Dispatchers.IO) {
                    db.favoriteGistDao().deleteById(favoriteGistId)
                }
            }
            onSuccess()
        } catch (e: Throwable) {
            onFailure(e)
        }

    }

    override fun isFavorite(
        gistId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {

        try {
            var exists: Boolean = false
            runBlocking {
                withContext(Dispatchers.IO) {
                    exists = db.favoriteGistDao().exists(gistId)
                }
            }
            onSuccess(exists)
        } catch (e: Throwable) {
            onFailure(e)
        }

    }

}