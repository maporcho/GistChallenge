package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.repository.implementation

import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {

    @GET("gists/public")
    fun getGists(@Query("page") page: Int, @Query("per_page") perPage: Int) : Call<List<Gist>>
}