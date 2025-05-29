package com.devj.dcine.core.data.api.dtos.video

import kotlinx.serialization.Serializable

@Serializable
data class VideoResponseDto(val id: Int, val results: List<VideoDto>)
