package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.repository

import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist

interface GistRepository {

    fun fetchGists(page: Int = 0, perPage: Int = 30, onSuccess: (List<Gist>) -> Unit, onFailure: (Throwable) -> Unit)

}