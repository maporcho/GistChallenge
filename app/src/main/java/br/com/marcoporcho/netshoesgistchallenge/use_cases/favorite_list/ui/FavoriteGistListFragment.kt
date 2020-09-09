package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.marcoporcho.netshoesgistchallenge.GistChallengeApplication
import br.com.marcoporcho.netshoesgistchallenge.R
import br.com.marcoporcho.netshoesgistchallenge.common.util.ImageUtil
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.FavoriteListContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.ui.FavoriteGistDetailFragment
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.ui.GistDetailActivity
import kotlinx.android.synthetic.main.favorite_gist_item_list.*
import kotlinx.android.synthetic.main.gist_item_list.listProgressBar
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.android.x.di
import org.kodein.di.instance


class FavoriteGistListFragment : Fragment(), FavoriteListContract.FavoriteGistListView, DIAware {

    override lateinit var presenter: FavoriteListContract.BaseFavoriteGistListPresenter

    override val di: DI by di()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.favorite_gist_item_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun onDestroy() {

        presenter.onDestroy()

        super.onDestroy()
    }

    private fun setupRecyclerView() {

        val favoriteGistListPresenter: FavoriteListContract.BaseFavoriteGistListPresenter by di.instance()

        setUp(favoriteGistListPresenter)

        favoriteGistListPresenter.loadGistList()

    }

    companion object {
        fun newInstance(): FavoriteGistListFragment = FavoriteGistListFragment()
    }

    class FavoriteGistItemRecyclerViewAdapter(
        private val parentFragment: FavoriteGistListFragment,
        private val values: ArrayList<FavoriteGist>

    ) :
            RecyclerView.Adapter<FavoriteGistItemRecyclerViewAdapter.ViewHolder>(), DIAware {

        override val di: DI by di(parentFragment.activity!!)

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as FavoriteGist

                try {
                    val intent = Intent(parentFragment.activity, GistDetailActivity::class.java).apply {
                        putExtra(FavoriteGistDetailFragment.ARG_FAVORITE_GIST_ID, item.id)
                    }
                    v.context.startActivity(intent)
                } catch (e: Exception) {
                    Log.d(GistChallengeApplication.TAG, "Error", e)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.gist_list_item_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val favoriteGist = values?.get(position)

            favoriteGist.avatar.let {
                holder.avatarImageView.setImageBitmap(ImageUtil.convert(favoriteGist.avatar!!))
            }


            holder.textGistOwner.text = favoriteGist?.owner
            holder.textGistType.text = favoriteGist?.type

            holder.favoriteImageView.setImageResource(R.drawable.baseline_grade_black_36)

            holder.favoriteImageView.setOnClickListener {

                val favoriteGistListPresenter: FavoriteListContract.BaseFavoriteGistListPresenter by di.instance()

                favoriteGistListPresenter.deleteFavoriteGist(favoriteGist, onSuccess = {
                    values.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, values.size)
                }, onFailure = {
                    Toast.makeText(parentFragment.activity, parentFragment.getString(R.string.favorite_error_deleting), Toast.LENGTH_LONG).show()
                })
            }

            with(holder.itemView) {
                tag = favoriteGist
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values?.size ?: 0

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val avatarImageView: ImageView = view.findViewById(R.id.imageAvatar)
            val textGistOwner: TextView = view.findViewById(R.id.textGistOwner)
            val textGistType: TextView = view.findViewById(R.id.textGistType)
            val favoriteImageView: ImageView = view.findViewById(R.id.imageFavorite)
        }
    }

    override fun handleLoadedList(favoriteGists: List<FavoriteGist>) {
        if(favoriteGists.isNotEmpty()) {
            recyclerViewFavoriteGistList.visibility = View.VISIBLE
            emptyView.visibility = View.GONE

            recyclerViewFavoriteGistList.adapter = FavoriteGistItemRecyclerViewAdapter(
                this,
                ArrayList(favoriteGists)
            )
        } else {
            recyclerViewFavoriteGistList.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
    }

    override fun setUp(presenter: FavoriteListContract.BaseFavoriteGistListPresenter) {
        this.presenter = presenter
        presenter.setView(this)
    }

    override fun isLoading(loading: Boolean) {
        listProgressBar.visibility = if(loading) ProgressBar.VISIBLE else ProgressBar.GONE
    }

    override fun handleError(t: Throwable) {
        Log.d(GistChallengeApplication.TAG, getString(R.string.favorite_error_adding), t)
        Toast.makeText(activity, getString(R.string.favorite_error_adding), Toast.LENGTH_LONG).show()
    }

}