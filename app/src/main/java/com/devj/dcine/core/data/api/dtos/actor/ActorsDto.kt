package com.devj.dcine.core.data.api.dtos.actor

import com.devj.dcine.core.utils.helpers.PathImageHelper
import com.devj.dcine.features.detail.domain.Actor
import com.devj.dcine.features.detail.domain.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDto (
    @SerialName("adult") val adult: Boolean,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("name") val name: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("profile_path") val profilePath: String?,
    @SerialName("cast_id") val castId: Int,
    @SerialName("character") val character: String,
    @SerialName("credit_id") val creditId: String,
    @SerialName("order") val order: Int
) {
    fun toDomain(): Actor {
       return Actor(
            adult = adult,
           gender = Gender.fromValue(gender),
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profilePath = profilePath?.let { PathImageHelper.getImageUrl(it) }  ?: "https://blog.springshare.com/wp-content/uploads/2010/02/nc-md.gif" ,
            castId = castId,
            character = character,
            creditId = creditId,
            order = order
       )
    }
}