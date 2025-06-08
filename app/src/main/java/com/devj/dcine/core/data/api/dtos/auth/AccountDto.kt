package com.devj.dcine.core.data.api.dtos.auth

import com.devj.dcine.features.auth.domain.models.Account
import com.devj.dcine.features.auth.domain.models.Avatar
import com.devj.dcine.features.auth.domain.models.Gravatar
import com.devj.dcine.features.auth.domain.models.Tmdb
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    val id: Int,
    val avatar: AvatarDto,
    @SerialName("iso_639_1")
    val iso6391: String,
    @SerialName("iso_3166_1")
    val iso31661:String,
    val name: String,
    @SerialName("include_adult")
    val includeAdult: Boolean,
    val username: String
) {
    fun toDomain(): Account {
        return Account(
            id = id,
            avatar = avatar.toDomain(),
            iso31661 = iso31661,
            iso6391 = iso6391,
            name = name,
            includeAdult = includeAdult,
            username = username
        )
    }
}

@Serializable
data class AvatarDto(val gravatar: GravatarHash, val tmdb: TMDBAvatar){
    fun toDomain() = Avatar(gravatar = gravatar.toDomain(), tmdb = tmdb.toDomain() )
}
@Serializable
data class GravatarHash(val hash: String){
    fun toDomain() = Gravatar(hash = hash)
}
@Serializable
data class TMDBAvatar(@SerialName("avatar_path") val avatarPath: String?){
    fun toDomain() = Tmdb(avatarPath = avatarPath)
}
