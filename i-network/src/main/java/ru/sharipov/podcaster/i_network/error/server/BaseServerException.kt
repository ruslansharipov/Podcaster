package ru.sharipov.podcaster.i_network.error.server

import ru.sharipov.podcaster.i_network.network.error.NetworkException
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

private fun prepareMessage(httpMessage: String, code: Int, url: String): String {
    return " serverCode=" + code + "\n" +
            ", httpMessage='" + httpMessage + "'" +
            ", url='" + url + "'"
}

/**
 * Ошибка от сервера
 */
sealed class BaseServerException(
    val serverCode: Int,
    httpMessage: String,
    url: String,
    cause: Exception?
) : NetworkException(prepareMessage(httpMessage, serverCode, url), cause)

/**
 * Специфичная ошибка возвращаемая сервером.
 * Если требуется обработать ошибку - нужно наследовать этот класс.
 */
abstract class SpecificServerException(
    code: Int,
    message: String?,
    url: String,
    cause: Exception?
) : BaseServerException(code, message ?: EMPTY_STRING, url, cause)

/**
 * Неизвестная ошибка
 */
class OtherHttpException(
    httpCode: Int,
    url: String,
    cause: Exception
) : BaseServerException(httpCode, "Неизвестная ошибка", url, cause)

/**
 * Отсутствует авторизация(401)
 */
class NonAuthorizedException(
    code: Int,
    httpMessage: String = "Пользователь не авторизован",
    url: String,
    cause: Exception
) : BaseServerException(code, httpMessage, url, cause)

/**
 * Данные не найдены. Сервер вернул 404 ошибку
 */
class DataNotFoundException(
    code: Int,
    httpMessage: String?,
    url: String,
    cause: Exception
) : BaseServerException(code, httpMessage ?: "Данные не найдены", url, cause)

/**
 * Внутреняя ошибка сервера. Сервер вернул 500 ошибку
 */
class ServerInternalException(
    code: Int,
    httpMessage: String?,
    url: String,
    cause: Exception
) : BaseServerException(code, httpMessage ?: "Внутреняя ошибка сервера", url, cause)

/**
 * Вы используете беслатный план и лимит запросов закончился
 */
class FreeLimitExceedException(
    code: Int,
    httpMessage: String?,
    url: String,
    cause: Exception
) : SpecificServerException(code, httpMessage ?: "Бесплатный лимит закончился", url, cause)