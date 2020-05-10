package ru.sharipov.podcaster.i_subscription

import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class Subscription(
    val id: String = EMPTY_STRING,
    val publisher: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val image: String = EMPTY_STRING
)