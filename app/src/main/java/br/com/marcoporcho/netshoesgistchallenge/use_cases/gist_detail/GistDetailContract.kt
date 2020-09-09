package br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail

import br.com.marcoporcho.netshoesgistchallenge.common.presenter.BasePresenter
import br.com.marcoporcho.netshoesgistchallenge.common.view.BaseView

interface GistDetailContract {
    interface BaseGistDetailPresenter<T> : BasePresenter {
        fun setView(v: GistDetailView<T>)
        fun loadGist(t: T)
    }

    interface GistDetailView<T> : BaseView<BaseGistDetailPresenter<T>> {
        fun handleLoadedGist(t:T?)
    }
}