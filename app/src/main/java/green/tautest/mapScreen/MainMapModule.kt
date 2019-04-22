package green.tautest.mapScreen

import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MainMapModule {

    @Binds
    abstract fun view(view: MainMapActivity): View

    @Binds
    abstract fun presenter(presenter: MainMapPresenter): Presenter

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun rxPermissions(activity: MainMapActivity) = RxPermissions(activity)
    }

}