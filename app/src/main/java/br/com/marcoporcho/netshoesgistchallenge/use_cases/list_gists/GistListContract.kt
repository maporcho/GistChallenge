package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists

import br.com.marcoporcho.netshoesgistchallenge.common.presenter.BasePresenter
import br.com.marcoporcho.netshoesgistchallenge.common.view.BaseView
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist

interface GistListContract {
    interface BaseGistListPresenter : BasePresenter {
        fun setView(v: GistListView)
        fun loadList(page: Int = 0, perPage: Int = 30, onSuccess: (List<Gist>) -> Unit, onFailure: (Throwable) -> Unit)
        fun deleteFavoriteGist(gistId: String, onSuccess: () ->Unit, onFailure: (Throwable) -> Unit)
        fun insertFavoriteGist(gist: Gist, onSuccess: () ->Unit, onFailure: (Throwable) -> Unit)
    }

    interface GistListView : BaseView<BaseGistListPresenter> {
        override fun handleError(t: Throwable) {
            //nothing to do here
        }

    }
}