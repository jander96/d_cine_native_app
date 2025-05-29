package com.devj.dcine.features.video.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

enum class VideoSite {
    YOUTUBE,
    VIMEO,
    DAILYMOTION,
    UNKNOWN;

    companion object {
        fun fromSite(site: String): VideoSite {
            return when (site) {
                "YouTube" -> YOUTUBE
                "Vimeo" -> VIMEO
                "Dailymotion" -> DAILYMOTION
                else -> UNKNOWN
            }
        }
    }
}

enum class VideoType {
    TRAILER,
    TEASER,
    CLIP,
    FEATURETTE,
    BEHIND_THE_SCENES,
    BLOOPERS,
    OPENING_CREDITS,
    RECAP,
    INTERVIEW,
    UNKNOWN;

    companion object {
        fun fromType(type: String): VideoType {
            return when (type) {
                "Trailer" -> TRAILER
                "Teaser" -> TEASER
                "Clip" -> CLIP
                "Featurette" -> FEATURETTE
                "Behind the Scenes" -> BEHIND_THE_SCENES
                "Bloopers" -> BLOOPERS
                "Opening Credits" -> OPENING_CREDITS
                "Recap" -> RECAP
                "Interview" -> INTERVIEW
                else -> UNKNOWN
            }
        }
    }
}

@Parcelize
data class Video(
    val iso6391: String,
    val iso31661: String,
    val name: String,
    val key: String,
    val site: VideoSite,
    val size: Int,
    val type: VideoType,
    val official: Boolean,
    val publishedAt: String,
    val id: String
): Parcelable {
    fun getUrl(): String {
        return when (site) {
            VideoSite.YOUTUBE -> "https://www.youtube.com/watch?v=$key"
            VideoSite.VIMEO -> "https://vimeo.com/$key"
            VideoSite.DAILYMOTION -> "https://www.dailymotion.com/video/$key"
            VideoSite.UNKNOWN -> "https://www.youtube.com/watch?v=$key"
        }
    }

    fun getThumbnailUrl(): String {
        return when (site) {
            VideoSite.YOUTUBE -> "https://img.youtube.com/vi/$key/hqdefault.jpg"
            VideoSite.VIMEO -> "https://vumbnail.com/$key.jpg"
            VideoSite.DAILYMOTION -> "https://www.dailymotion.com/thumbnail/video/$key"
            VideoSite.UNKNOWN -> "https://img.youtube.com/vi/$key/hqdefault.jpg"
        }
    }

    fun getEmbedUrl(): String {
        return when (site) {
            VideoSite.YOUTUBE -> "https://www.youtube.com/embed/$key"
            VideoSite.VIMEO -> "https://player.vimeo.com/video/$key"
            VideoSite.DAILYMOTION -> "https://www.dailymotion.com/embed/video/$key"
            VideoSite.UNKNOWN -> "https://www.youtube.com/embed/$key"
        }
    }

    fun getSizeInMb(): Double {
        return size / 1024.0 // Assuming size is in KB, convert to MB
    }

    fun getSimpleSize(): String {
        return when {
            size < 1024 -> "$size KB"
            size < 1048576 -> "${size / 1024} MB"
            else -> "${size / 1048576} GB"
        }
    }
}
