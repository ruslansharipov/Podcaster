package ru.sharipov.podcaster.domain

data class Extra(
    val youtubeUrl: String = EMPTY_STRING,
    val facebookHandle: String = EMPTY_STRING,
    val instagramHandle: String = EMPTY_STRING,
    val twitterHandle: String = EMPTY_STRING,
    val wechatHandle: String = EMPTY_STRING,
    val patreonHandle: String = EMPTY_STRING,
    val googleUrl: String = EMPTY_STRING,
    val linkedinUrl: String = EMPTY_STRING,
    val spotifyUrl: String = EMPTY_STRING,
    val url1: String = EMPTY_STRING,
    val url2: String = EMPTY_STRING,
    val url3: String = EMPTY_STRING
)