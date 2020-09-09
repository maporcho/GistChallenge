package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.presenter

import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.FavoriteGistRepository
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.GistListContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.repository.GistRepository

class GistListPresenter(val gistRepository: GistRepository, val favoriteGistRepository: FavoriteGistRepository): GistListContract.BaseGistListPresenter{

    var v: GistListContract.GistListView? = null

    override fun setView(v: GistListContract.GistListView) {
        this.v = v
    }


    override fun loadList(
        page: Int,
        perPage: Int,
        onSuccess: (List<Gist>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        v?.isLoading(true)
        gistRepository.fetchGists(
            page=page,
            perPage = perPage,
            onSuccess = {
                onSuccess(it)
                v?.isLoading(false)
            },
            onFailure = {
                onFailure(it)
                v?.isLoading(false)
            }
        )
    }

    override fun deleteFavoriteGist(gistId: String, onSuccess: () ->Unit, onFailure: (Throwable) -> Unit) {
        favoriteGistRepository.delete(
            gistId,
            onSuccess = {
                onSuccess()
            },
            onFailure = {throwable ->
                onFailure(throwable)
            }
        )
    }

    override fun insertFavoriteGist(
        gist: Gist,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        favoriteGistRepository.insert(
            gist,
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