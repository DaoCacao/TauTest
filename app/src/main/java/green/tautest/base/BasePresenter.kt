package green.tautest.base

import javax.inject.Inject

abstract class BasePresenter<V : MvpView> : MvpPresenter {

    @Inject lateinit var view: V
}