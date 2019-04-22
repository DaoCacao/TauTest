package green.tautest.extentions

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.saveSubscribe(onSuccess: (T) -> Unit): Disposable = this.subscribe(onSuccess, { e -> Log.e("ERROR", e.message) })
fun Completable.saveSubscribe(onSuccess: () -> Unit): Disposable = this.subscribe(onSuccess, { e -> Log.e("ERROR", e.message) })

fun <T> Single<T>.scheduleIo(): Single<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
fun Completable.scheduleIo(): Completable = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
fun <T> Flowable<T>.scheduleIo(): Flowable<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())