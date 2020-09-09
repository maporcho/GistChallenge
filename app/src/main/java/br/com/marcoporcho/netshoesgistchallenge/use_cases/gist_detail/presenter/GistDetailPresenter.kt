package br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.presenter

import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.GistDetailContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist

class GistDetailPresenter: GistDetailContract.BaseGistDetailPresenter<Gist>{

    var v: GistDetailContract.GistDetailView<Gist>? = null

    override fun loadGist(gist: Gist) {
        v?.handleLoadedGist(gist)
    }

    override fun onDestroy() {
        v = null
    }

    override fun setView(v: GistDetailContract.GistDetailView<Gist>) {
        this.v = v
    }

}