package ru.sharipov.podcaster.i_network.error

/**
 * типы специфичных ошибок сервера
 *
 * @param code код ошибки
 */
enum class ApiErrorType(vararg val codes: Int) {

    FREE_LIMIT_EXCEED(429), // you are using FREE plan and you exceed the quota limit
    UNKNOWN(-1);

    companion object {

        fun getByCode(code: Int?): ApiErrorType =
            values().find { error -> error.codes.any { it == code } } ?: UNKNOWN
    }
}
