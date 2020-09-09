package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list

import br.com.marcoporcho.netshoesgistchallenge.common.presenter.BasePresenter
import br.com.marcoporcho.netshoesgistchallenge.common.view.BaseView
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist

interface FavoriteListContract {
    interface BaseFavoriteGistListPresenter : BasePresenter {
        fun setView(v: FavoriteGistListView)
        fun loadGistList()
        fun deleteFavoriteGist(favoriteGist: FavoriteGist, onSuccess: () ->Unit, onFailure: (Throwable) -> Unit)
    }

    interface FavoriteGistListView : BaseView<BaseFavoriteGistListPresenter> {
        fun handleLoadedList(gistList: List<FavoriteGist>)
    }
}