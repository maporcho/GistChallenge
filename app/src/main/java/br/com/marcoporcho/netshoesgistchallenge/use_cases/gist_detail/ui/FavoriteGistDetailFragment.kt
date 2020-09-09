package br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.marcoporcho.netshoesgistchallenge.GistChallengeApplication
import br.com.marcoporcho.netshoesgistchallenge.R
import br.com.marcoporcho.netshoesgistchallenge.common.util.ImageUtil
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.GistDetailContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.presenter.FavoriteGistDetailPresenter
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class FavoriteGistDetailFragment : Fragment(), GistDetailContract.GistDetailView<FavoriteGist>, DIAware {

    override lateinit var presenter: GistDetailContract.BaseGistDetailPresenter<FavoriteGist>

    override val di: DI by di()

    private var item: FavoriteGist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val favoriteGistPresenter: FavoriteGistDetailPresenter by di.instance()

        setUp(favoriteGistPresenter)

        arguments?.let {
            if (it.containsKey(ARG_FAVORITE_GIST_ID)) {

                favoriteGistPresenter.loadGist(FavoriteGist(it.getString(ARG_FAVORITE_GIST_ID)!!))

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        item?.let {

            rootView.findViewById<TextView>(R.id.textDetailGistOwner).text = it.owner
            rootView.findViewById<TextView>(R.id.textDetailGistUrl).text = it.htmlUrl
            rootView.findViewById<TextView>(R.id.textDetailGistDescription).text = it.description
        }

        return rootView
    }

    override fun onDestroy() {
        this.presenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        const val ARG_FAVORITE_GIST_ID = "item_favorite_gist_id"
    }

    override fun handleLoadedGist(favoriteGist: FavoriteGist?) {
        item = favoriteGist

        item!!.avatar.let {avatarBase64 ->
            activity?.findViewById<ImageView>(R.id.bgImage)?.setImageBitmap(ImageUtil.convert(avatarBase64!!))
        }
    }

    override fun setUp(presenter: GistDetailContract.BaseGistDetailPresenter<FavoriteGist>) {
        this.presenter = presenter
        presenter.setView(this)
    }

    override fun isLoading(loading: Boolean) {
        //no loading in this page
    }

    override fun handleError(throwable: Throwable) {
        Log.d(GistChallengeApplication.TAG, getString(R.string.favorite_error_fetching_gist), throwable)
        Toast.makeText(activity, R.string.favorite_error_fetching_gist, Toast.LENGTH_LONG).show()
    }
}