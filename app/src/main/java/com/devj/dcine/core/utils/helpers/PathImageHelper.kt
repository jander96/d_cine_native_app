package com.devj.dcine.core.utils.helpers

object PathImageHelper {
    fun getImageUrl(path: String): String {
        return path.let { "https://image.tmdb.org/t/p/w500/${it}" }
    }
}