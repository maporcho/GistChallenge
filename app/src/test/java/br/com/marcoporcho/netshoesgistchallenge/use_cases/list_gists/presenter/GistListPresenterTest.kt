package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.presenter

import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.FavoriteGistRepository
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.GistDetailContract
import br.com.marcoporcho.netshoesgistchallenge.use_cases.gist_detail.presenter.FavoriteGistDetailPresenter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.any
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class FavoriteGistDetailPresenterTest {

    @Mock
    private lateinit var mockView: GistDetailContract.GistDetailView<FavoriteGist>

    val stubFavoriteGistRepository: FavoriteGistRepository = FavoriteGistRepositoryStub()

    private var presenter: FavoriteGistDetailPresenter? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = FavoriteGistDetailPresenter(stubFavoriteGistRepository)
        presenter?.setView(mockView)
    }

    @Test
    fun testLoadGist() {
        presenter?.loadGist(FavoriteGist(id = "abc"))
        verify(mockView).handleLoadedGist(any())
    }
}

class FavoriteGistRepositoryStub: FavoriteGistRepository {
    override fun fetchAll(onSuccess: (List<FavoriteGist>) -> Unit, onFailure: (Throwable) -> Unit) {

    }

    override fun fetch(
        favoriteGistId: String,
        onSuccess: (FavoriteGist?) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        onSuccess(FavoriteGist(id = "abc"))
    }

    override fun insert(gist: Gist, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {

    }

    override fun delete(
        favoriteGist: FavoriteGist,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {

    }

    override fun delete(gistId: String, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {

    }

    override fun isFavorite(
        gistId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {

    }

}