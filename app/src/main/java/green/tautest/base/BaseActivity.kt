package green.tautest.base

import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<P : MvpPresenter> : DaggerAppCompatActivity(), MvpView {

    @Inject lateinit var presenter: P
}