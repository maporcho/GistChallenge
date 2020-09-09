package br.com.marcoporcho.netshoesgistchallenge.common.view

import br.com.marcoporcho.netshoesgistchallenge.common.presenter.BasePresenter

interface BaseView<T: BasePresenter> {

    val presenter: T

    fun setUp(presenter : T)

    fun isLoading(loading: Boolean)

    fun handleError(t: Throwable)
}