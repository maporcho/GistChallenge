package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.ui

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marcoporcho.netshoesgistchallenge.GistChallengeApplication
import br.com.marcoporcho.netshoesgistchallenge.R
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.ui.GistDetailActivity
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.ui.GistDetailFragment
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.GistListContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.gist_item_list.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance


class GistListFragment : Fragment(), GistListContract.GistListView, DIAware {

    override lateinit var presenter: GistListContract.BaseGistListPresenter

    override val di: DI by di()

    var notLoading = true
    var page = 0
    var gists: ArrayList<Any> = arrayListOf()

    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: GistItemRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.gist_item_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(recyclerViewGistList)
    }

    override fun onDestroy() {
        this.presenter.onDestroy()
        super.onDestroy()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val gistPresenter: GistListContract.BaseGistListPresenter by di.instance()


        gistPresenter.loadList(
            onSuccess = { gistList ->

                page++

                gists.addAll(gistList)

                layoutManager = LinearLayoutManager(activity)

                recyclerView.layoutManager = layoutManager

                adapter = GistItemRecyclerViewAdapter(gistPresenter, gists)

                adapter.setHasStableIds(true)

                recyclerView.adapter = adapter

                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if (notLoading && layoutManager.findLastCompletelyVisibleItemPosition() == gists.size - 1) {
                            gists.add(LoadingPlaceHolder())
                            adapter.notifyItemInserted(gists.size - 1)

                            notLoading = false
                            gistPresenter.loadList(
                                page = ++page,
                                onSuccess = { returnedGists ->
                                    gists.removeAt(gists.size - 1)
                                    adapter.notifyItemRemoved(gists.size)
                                    if (returnedGists.isNotEmpty()) {
                                        gists.addAll(returnedGists)
                                        adapter.notifyDataSetChanged()
                                        notLoading = true
                                    } else {
                                        Toast.makeText(
                                            activity,
                                            "End of data reached",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                },
                                onFailure = {
                                    Log.d(
                                        GistChallengeApplication.TAG,
                                        getString(R.string.gists_error_fetching),
                                        it
                                    )
                                    Toast.makeText(
                                        activity,
                                        R.string.gists_error_fetching,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )

                        }
                    }
                })



            },
            onFailure = {
                Log.d(GistChallengeApplication.TAG, getString(R.string.gists_error_fetching), it)
                Toast.makeText(activity, R.string.gists_error_fetching, Toast.LENGTH_LONG).show()

                recyclerViewGistList.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }
        )

    }

    companion object {
        fun newInstance(): GistListFragment = GistListFragment()
    }

    class GistItemRecyclerViewAdapter(
        private val gistPresenter: GistListContract.BaseGistListPresenter,
        private val values: List<Any>?

    ) :
            RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Gist

                val intent = Intent(v.context, GistDetailActivity::class.java).apply {
                    putExtra(GistDetailFragment.ARG_GIST, item)
                }
                v.context.startActivity(intent)

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            lateinit var holder: RecyclerView.ViewHolder
            if(viewType == TYPE_GIST) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.gist_list_item_content, parent, false)
                holder = GistViewHolder(view)
                holder.setIsRecyclable(false)
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading, parent, false)
                holder = LoadingViewHolder(view)
                holder.setIsRecyclable(false)
            }
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            if (getItemViewType(position) == TYPE_GIST) {

                holder as GistViewHolder

                val gist = values?.get(position) as Gist
                gist?.owner?.avatarUrl.let {
                    Picasso.get()
                        .load(it)
                        .placeholder(R.drawable.baseline_account_circle_black_24)
                        .error(R.drawable.baseline_account_circle_black_24)
                        .into(holder.avatarImageView)
                }

                holder.textGistOwner.text = gist?.owner?.login ?: "Anonymous"
                holder.textGistType.text =
                    (gist?.files?.values?.firstOrNull()?.getValue("type") ?: "Unknown") as String

                holder.favoriteImageView.setImageResource(if (gist?.favorite == true) R.drawable.baseline_grade_black_36 else R.drawable.outline_grade_black_36)

                holder.favoriteImageView.setOnClickListener {
                    gist!!.favorite = !gist!!.favorite
                    if (gist!!.favorite) {
                        gistPresenter.insertFavoriteGist(
                            gist,
                            onSuccess = {
                                Log.d(
                                    GistChallengeApplication.TAG,
                                    "Success adding favorite: $gist"
                                )
                                holder.favoriteImageView.setImageResource(R.drawable.baseline_grade_black_36)

                            },
                            onFailure = {
                                Log.d(
                                    GistChallengeApplication.TAG,
                                    "Error adding favorite: $gist\nError: $it"
                                )
                            }
                        )
                    } else {
                        gistPresenter.deleteFavoriteGist(
                            gist!!.id,
                            onSuccess = {
                                Log.d(
                                    GistChallengeApplication.TAG,
                                    "Success deleting favorite: $gist"
                                )
                                holder.favoriteImageView.setImageResource(R.drawable.outline_grade_black_36)
                            },
                            onFailure = {
                                Log.d(
                                    GistChallengeApplication.TAG,
                                    "Error deleting favorite: $gist\nError: $it"
                                )
                            }
                        )
                    }
                }


                with(holder.itemView) {
                    tag = gist
                    setOnClickListener(onClickListener)
                }
            } else {

            }
        }

        override fun getItemCount() = values?.size ?: 0

        override fun getItemViewType(position: Int): Int {
            return if(values?.get(position) is Gist){
                TYPE_GIST
            } else {
                TYPE_LOADING
            }
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        companion object {
            const val TYPE_GIST = 1
            const val TYPE_LOADING = 2
        }
    }

    override fun setUp(presenter: GistListContract.BaseGistListPresenter) {
        this.presenter = presenter
        presenter.setView(this)
    }

    override fun isLoading(loading: Boolean) {
        listProgressBar.visibility = if(loading) ProgressBar.VISIBLE else ProgressBar.GONE
    }


}

private class GistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var avatarImageView: ImageView = view.findViewById(R.id.imageAvatar)
    var textGistOwner: TextView = view.findViewById(R.id.textGistOwner)
    var textGistType: TextView = view.findViewById(R.id.textGistType)
    var favoriteImageView: ImageView = view.findViewById(R.id.imageFavorite)
}

private class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view)

private class LoadingPlaceHolder