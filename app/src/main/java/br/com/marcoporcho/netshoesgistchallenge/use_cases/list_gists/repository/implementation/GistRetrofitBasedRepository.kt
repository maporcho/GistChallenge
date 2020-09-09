package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.repository.implementation

import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.FavoriteGistRepository
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.repository.GistRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GistRetrofitBasedRepository(var path: String, val favoriteGistRepository: FavoriteGistRepository):
    GistRepository {
    override fun fetchGists(
        page: Int,
        perPage: Int,
        onSuccess: (List<Gist>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {

        val client = getRetrofitInstance(path = path)

        val endpoint = client.create(Endpoint::class.java)

        val callback = endpoint.getGists(page = page, perPage = perPage)


        callback.enqueue(object: Callback<List<Gist>> {
            override fun onResponse(call: Call<List<Gist>>, response: Response<List<Gist>>) {
                val gists = response.body()

                favoriteGistRepository.fetchAll(
                    onSuccess = { favoriteGists ->
                        val favoriteGistsIds = favoriteGists.map { it.id };

                        gists?.forEach { gist ->
                            val favorite = favoriteGistsIds.contains(gist.id)
                            gist.favorite = favorite
                        }

                        onSuccess(gists ?: listOf())
                    },
                    onFailure = {
                        //doesn't set any gists as favorite
                        print("Failure fetching all gists")
                    }
                )

            }

            override fun onFailure(call: Call<List<Gist>>, t: Throwable) {
                onFailure(t)
            }

        })

    }


    private fun getRetrofitInstance(path: String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}



