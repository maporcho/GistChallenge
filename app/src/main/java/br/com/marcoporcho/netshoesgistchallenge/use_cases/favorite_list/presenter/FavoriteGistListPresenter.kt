package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.presenter

import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.FavoriteListContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.FavoriteGistRepository

class FavoriteGistListPresenter(val favoriteGistRepository: FavoriteGistRepository): FavoriteListContract.BaseFavoriteGistListPresenter{

    var v: FavoriteListContract.FavoriteGistListView? = null

    override fun setView(v: FavoriteListContract.FavoriteGistListView) {
        this.v = v
    }

    override fun loadGistList() {
        v?.isLoading(true)
        favoriteGistRepository.fetchAll(
            onSuccess = { favoriteGists ->
                v?.isLoading(false)
                v?.handleLoadedList(favoriteGists)
            },
            onFailure = { throwable ->
                v?.isLoading(false)
                v?.handleError(throwable)
            }
        )
    }

    override fun deleteFavoriteGist(favoriteGist: FavoriteGist, onSuccess: () ->Unit, onFailure: (Throwable) -> Unit) {
        favoriteGistRepository.delete(
            favoriteGist,
            onSuccess = {
                onSuccess()
            },
            onFailure = {throwable ->
                onFailure(throwable)
            }
        )
    }

    override fun onDestroy() {
        v = null
    }


}