package br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.marcoporcho.netshoesgistchallenge.R
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.GistDetailContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.presenter.GistDetailPresenter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import com.squareup.picasso.Picasso
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class GistDetailFragment : Fragment(), GistDetailContract.GistDetailView<Gist>, DIAware {

    override lateinit var presenter: GistDetailContract.BaseGistDetailPresenter<Gist>

    override val di: DI by di()

    private var item: Gist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gistPresenter: GistDetailPresenter by di.instance()

        setUp(gistPresenter)

        arguments?.let {
            if (it.containsKey(ARG_GIST)) {
                item = it.getParcelable(ARG_GIST)!!

                gistPresenter.loadGist(item!!)

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        item?.let {

            rootView.findViewById<TextView>(R.id.textDetailGistOwner).text = it.owner?.login
            rootView.findViewById<TextView>(R.id.textDetailGistUrl).text = it.htmlUrl
            rootView.findViewById<TextView>(R.id.textDetailGistDescription).text = if(it.description.isNullOrBlank()) "None" else it.description
        }

        return rootView
    }

    override fun onDestroy() {
        this.presenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        const val ARG_GIST = "item_gist"
    }

    override fun handleLoadedGist(gist: Gist?) {
        gist?.owner?.avatarUrl.let { avatarUrl ->
            Picasso.get()
                .load(avatarUrl)
                .placeholder(R.drawable.baseline_account_circle_black_24)
                .error(R.drawable.baseline_account_circle_black_24)
                .into(activity?.findViewById<ImageView>(R.id.bgImage))
        }
    }

    override fun setUp(presenter: GistDetailContract.BaseGistDetailPresenter<Gist>) {
        this.presenter = presenter
        presenter.setView(this)
    }

    override fun isLoading(loading: Boolean) {
        //there's no loading in this screen
    }

    override fun handleError(t: Throwable) {
        //there's no error handling in this screen
    }
}