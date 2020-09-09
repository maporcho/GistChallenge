package br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.fragment.app.Fragment
import br.com.marcoporcho.netshoesgistchallenge.R
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.ui.GistListFragment

class GistDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var fragment: Fragment? = null

        if (savedInstanceState == null) {
            if(intent.getParcelableExtra<Gist>(GistDetailFragment.ARG_GIST) != null) {
                fragment = GistDetailFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putParcelable(
                                GistDetailFragment.ARG_GIST,
                                intent.getParcelableExtra<Gist>(GistDetailFragment.ARG_GIST)
                            )
                        }
                    }
            } else if(intent.getStringExtra(FavoriteGistDetailFragment.ARG_FAVORITE_GIST_ID) != null) {
                fragment = FavoriteGistDetailFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putString(
                                FavoriteGistDetailFragment.ARG_FAVORITE_GIST_ID,
                                intent.getStringExtra(FavoriteGistDetailFragment.ARG_FAVORITE_GIST_ID)
                            )
                        }
                    }
            }


            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment!!)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {

                    navigateUpTo(Intent(this, GistListFragment::class.java))

                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}