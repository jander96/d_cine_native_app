package com.devj.dcine.core.data.api.dtos.video

import com.devj.dcine.features.video.domain.model.Video
import com.devj.dcine.features.video.domain.model.VideoSite
import com.devj.dcine.features.video.domain.model.VideoType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    @SerialName("iso_639_1")
    val iso6391: String,
    @SerialName("iso_3166_1")
    val iso31661: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    @SerialName("published_at")
    val publishedAt: String,
    val id: String
) {
    fun toDomain() = Video(
        iso6391 = iso6391,
        iso31661 = iso31661,
        name = name,
        key = key,
        site = VideoSite.fromSite(site),
        size = size,
        type = VideoType.fromType(type),
        official = official,
        publishedAt = publishedAt,
        id = id
    )
}

