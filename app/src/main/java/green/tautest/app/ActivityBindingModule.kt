package green.tautest.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import green.tautest.mapScreen.MainMapActivity
import green.tautest.mapScreen.MainMapModule

@Module
interface ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainMapModule::class])
    fun movieList(): MainMapActivity
}