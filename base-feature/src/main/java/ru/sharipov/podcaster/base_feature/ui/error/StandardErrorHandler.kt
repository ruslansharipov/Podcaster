package ru.sharipov.podcaster.base_feature.ui.error

import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.ui.message_controller.IconMessageController
import ru.sharipov.podcaster.i_network.error.NetworkErrorHandler
import ru.sharipov.podcaster.i_network.error.server.BaseServerException
import ru.sharipov.podcaster.i_network.error.server.NonAuthorizedException
import ru.sharipov.podcaster.i_network.error.server.SpecificServerException
import ru.sharipov.podcaster.i_network.network.error.ConversionException
import ru.sharipov.podcaster.i_network.network.error.HttpCodes
import ru.sharipov.podcaster.i_network.network.error.NoInternetException
import ru.surfstudio.android.core.ui.navigation.activity.navigator.GlobalNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.logger.Logger
import javax.inject.Inject

/**
 * Стандартный обработчик ошибок, возникающих при работе с сервером
 */
@PerScreen
open class StandardErrorHandler @Inject constructor(
    private val globalNavigator: GlobalNavigator,
    private val messageController: IconMessageController
) : NetworkErrorHandler() {

    override fun handleHttpProtocolException(e: BaseServerException) {
        if (e is NonAuthorizedException) {
            // TODO
            return
        }
        when {
            e.serverCode >= HttpCodes.CODE_500 -> messageController.show(R.string.server_error_message)
            e.serverCode == HttpCodes.CODE_429 -> messageController.show(R.string.free_limit_exceed)
            e.serverCode == HttpCodes.CODE_404 -> messageController.show(R.string.server_error_not_found)
            else -> when (e) {
                is SpecificServerException -> Unit // do nothing
                else -> messageController.show(R.string.default_http_error_message)
            }
        }

        // Логгируем все 4** и 5** ошибки
        // RemoteLogger.logError(e)
    }

    override fun handleNoInternetError(e: NoInternetException) {
        messageController.show(R.string.no_internet_connection_error_message)
    }

    override fun handleConversionError(e: ConversionException) {
        messageController.show(R.string.bad_response_error_message)
    }

    override fun handleOtherError(e: Throwable) {
        messageController.show(R.string.unexpected_error_error_message)
        Logger.e(e, "Unexpected error")
    }
}
