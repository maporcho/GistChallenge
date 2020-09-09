package br.com.marcoporcho.netshoesgistchallenge

import android.app.Application
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.FavoriteListContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.presenter.FavoriteGistListPresenter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.FavoriteGistRepository
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.converter.FavoriteGistConverter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.converter.implementation.FavoriteGistDownloaderConverter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.implementation.FavoriteGistDbRepository
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.presenter.FavoriteGistDetailPresenter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.presenter.GistDetailPresenter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.GistListContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.presenter.GistListPresenter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.repository.GistRepository
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.repository.implementation.GistRetrofitBasedRepository
import org.kodein.di.*

class GistChallengeApplication: Application (), DIAware {

    override val di by DI.lazy {
        bind<FavoriteGistConverter>() with singleton { FavoriteGistDownloaderConverter() }
        bind<FavoriteGistRepository>() with singleton { FavoriteGistDbRepository(context = applicationContext, favoriteGistConverter = instance()) }
        bind<GistRepository>() with singleton { GistRetrofitBasedRepository(path = "https://api.github.com/", favoriteGistRepository = instance()) }
        bind<FavoriteGistDetailPresenter>() with provider { FavoriteGistDetailPresenter(instance()) }
        bind<GistDetailPresenter>() with provider { GistDetailPresenter() }
        bind<FavoriteListContract.BaseFavoriteGistListPresenter>() with provider { FavoriteGistListPresenter(instance()) }
        bind<GistListContract.BaseGistListPresenter>() with provider { GistListPresenter(instance(), instance()) }
    }

    companion object {
        const val TAG = "GIST_CHALLENGE_APP"
    }
}
