package ru.sharipov.podcaster.i_network.network

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.HttpException
import ru.sharipov.podcaster.i_network.error.server.*
import ru.sharipov.podcaster.i_network.network.calladapter.BaseCallAdapterFactory
import ru.sharipov.podcaster.i_network.network.error.HttpCodes
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

@PerApplication
class CallAdapterFactory : BaseCallAdapterFactory() {

    override fun <R> onHttpException(e: HttpException, call: Call<R>): Observable<R> {

        val response = e.response()?.raw()
        val url = response?.request?.url?.toString() ?: EMPTY_STRING

        val httpError: BaseServerException = when (e.code()) {
            HttpCodes.CODE_429 -> FreeLimitExceedException(e.code(), e.message(), url, e)
            HttpCodes.CODE_401 -> NonAuthorizedException(e.code(), e.message(), url, e)
            HttpCodes.CODE_404 -> DataNotFoundException(e.code(), e.message(), url, e)
            HttpCodes.CODE_500 -> ServerInternalException(e.code(), e.message(), url, e)
            else -> OtherHttpException(e.code(), url, e)
        }
        return Observable.error<R>(httpError)
    }
}
