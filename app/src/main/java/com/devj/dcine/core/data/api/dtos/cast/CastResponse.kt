package com.devj.dcine.core.data.api.dtos.cast

import com.devj.dcine.core.data.api.dtos.actor.ActorDto
import com.devj.dcine.features.detail.domain.Actor
import kotlinx.serialization.Serializable


@Serializable
data class CastResponse(val id: Int, val cast: List<ActorDto>) {
    fun toDomain(): List<Actor> =
        cast.map { it.toDomain() }
}