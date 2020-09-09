package br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.presenter

import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.FavoriteGistRepository
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.GistDetailContract

class FavoriteGistDetailPresenter(val favoriteGistRepository: FavoriteGistRepository): GistDetailContract.BaseGistDetailPresenter<FavoriteGist>{

    var v: GistDetailContract.GistDetailView<FavoriteGist>? = null

    override fun loadGist(favoriteGist: FavoriteGist) {
        favoriteGistRepository.fetch(
            favoriteGist.id,
            onSuccess = {favoriteGist ->
                v?.handleLoadedGist(favoriteGist)
            },
            onFailure = {throwable ->
                v?.handleError(throwable)
            }
        )
    }

    override fun onDestroy() {
        v = null
    }

    override fun setView(v: GistDetailContract.GistDetailView<FavoriteGist>) {
        this.v = v
    }

}