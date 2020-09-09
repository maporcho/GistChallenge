package br.com.marcoporcho.netshoesgistchallenge

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import br.com.marcoporcho.netshoesgistchallenge.GistChallengeApplication
import br.com.marcoporcho.netshoesgistchallenge.R
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.ui.FavoriteGistListFragment
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.ui.GistListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        bottomNavigation.selectedItemId = R.id.navigation_gist_list


    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_gist_list -> {
                toolbar.title = getString(R.string.item_label_list)
                val gistListFrament = GistListFragment.newInstance()
                openFragment(gistListFrament)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite_gists -> {
                toolbar.title = getString(R.string.item_label_favorite)
                val gistListFrament = FavoriteGistListFragment.newInstance()
                openFragment(gistListFrament)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}