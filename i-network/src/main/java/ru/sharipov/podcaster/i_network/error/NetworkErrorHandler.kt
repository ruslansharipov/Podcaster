package ru.sharipov.podcaster.i_network.error

import io.reactivex.exceptions.CompositeException
import ru.sharipov.podcaster.i_network.error.server.BaseServerException
import ru.surfstudio.android.logger.Logger
import ru.sharipov.podcaster.i_network.network.error.ConversionException
import ru.sharipov.podcaster.i_network.network.error.NetworkException
import ru.sharipov.podcaster.i_network.network.error.NoInternetException
import ru.surfstudio.android.core.mvp.error.ErrorHandler

/**
 * Базовый класс для обработки ошибок, возникающий при работе с Observable из слоя Interactor
 */
abstract class NetworkErrorHandler : ErrorHandler {

    override fun handleError(err: Throwable) {
        Logger.i(err, "NetworkErrorHandler handle error")
        when (err) {
            is CompositeException -> handleCompositeException(err)
            is ConversionException -> handleConversionError(err)
            is BaseServerException -> handleHttpProtocolException(err)
            is NoInternetException -> handleNoInternetError(err)
            else -> handleOtherError(err)
        }
    }

    /**
     * @param err - CompositeException может возникать при комбинировании Observable
     */
    private fun handleCompositeException(err: CompositeException) {
        val exceptions = err.exceptions
        var networkException: NetworkException? = null
        var otherException: Throwable? = null
        for (e in exceptions) {
            if (e is NetworkException) {
                if (networkException == null) {
                    networkException = e
                }
            } else if (otherException == null) {
                otherException = e
            }
        }
        if (networkException != null) {
            handleError(networkException)
        }
        if (otherException != null) {
            handleOtherError(otherException)
        }
    }

    protected abstract fun handleHttpProtocolException(e: BaseServerException)

    protected abstract fun handleNoInternetError(e: NoInternetException)

    protected abstract fun handleConversionError(e: ConversionException)

    protected abstract fun handleOtherError(e: Throwable)
}
