package ru.sharipov.podcaster.base.naming

import ru.surfstudio.android.filestorage.naming.NamingProcessor

/**
 * Конвертор по умолчанию
 */
class DefaultNamingProcessor: NamingProcessor {
    override fun getNameFrom(rawName: String): String = rawName
}