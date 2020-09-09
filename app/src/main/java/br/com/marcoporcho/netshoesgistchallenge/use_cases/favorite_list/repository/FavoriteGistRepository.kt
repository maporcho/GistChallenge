package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository

import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist

interface FavoriteGistRepository {

    fun fetchAll(onSuccess: (List<FavoriteGist>) -> Unit, onFailure: (Throwable)->Unit)

    fun fetch(favoriteGistId: String, onSuccess: (FavoriteGist?) -> Unit, onFailure: (Throwable)->Unit)

    fun insert(gist: Gist, onSuccess: ()->Unit, onFailure: (Throwable)->Unit)

    fun delete(favoriteGist: FavoriteGist, onSuccess: ()->Unit, onFailure: (Throwable)->Unit)

    fun delete(gistId: String, onSuccess: ()->Unit, onFailure: (Throwable)->Unit)

    fun isFavorite(gistId: String, onSuccess: (Boolean)->Unit, onFailure: (Throwable)->Unit)

}