package ru.dvaberega.data.remote

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import green.tautest.data.api.Api
import green.tautest.data.api.ApiManager
import green.tautest.data.api.IApiManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.MINUTES
import javax.inject.Singleton

@Module
abstract class ApiModule {

    @Binds
    abstract fun apiManager(manager: ApiManager): IApiManager

    @Module
    companion object {
        private const val CONNECTION_TIMEOUTS_MIN = 20L

        @Provides
        @JvmStatic
        @Singleton
        fun api(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

        @Provides
        @JvmStatic
        fun retrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.tau.green/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        @Provides
        @JvmStatic
        fun client(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUTS_MIN, MINUTES)
                .readTimeout(CONNECTION_TIMEOUTS_MIN, MINUTES)
                .writeTimeout(CONNECTION_TIMEOUTS_MIN, MINUTES)
                .addInterceptor(ChuckInterceptor(context))
                .build()
        }
    }
}
