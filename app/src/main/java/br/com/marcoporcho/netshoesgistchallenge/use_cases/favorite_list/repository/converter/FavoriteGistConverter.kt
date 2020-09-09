package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.converter

import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist

interface FavoriteGistConverter {
    fun toFavoriteGist(gist: Gist): FavoriteGist
}