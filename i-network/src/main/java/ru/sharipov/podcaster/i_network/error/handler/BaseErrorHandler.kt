package ru.sharipov.podcaster.i_network.error.handler

import ru.sharipov.podcaster.i_network.error.server.BaseServerException

/**
 * Базовый класс обработки ошибок сервера
 */
interface BaseErrorHandler {

    fun handle(e: BaseServerException)
}
