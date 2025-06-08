package com.devj.dcine.features.auth.domain.models


data class Account(
    val id: Int,
    val avatar: Avatar,
    val iso6391: String,
    val iso31661: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String
)


data class Avatar(val gravatar: Gravatar, val tmdb: Tmdb)

data class Gravatar(val hash: String)

data class Tmdb(val avatarPath: String?)