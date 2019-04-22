package green.tautest.app

import android.content.Context
import dagger.Module
import dagger.Provides
import green.tautest.data.location.FusedLocationModule
import ru.dvaberega.data.remote.ApiModule
import javax.inject.Singleton

@Module(includes = [ApiModule::class, FusedLocationModule::class])
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun context() = context
}