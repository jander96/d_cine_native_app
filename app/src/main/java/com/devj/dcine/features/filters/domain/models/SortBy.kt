package com.devj.dcine.features.filters.domain.models

enum class SortBy(val value: String) {
    POPULARITY_ASC("popularity.asc"),
    POPULARITY_DESC("popularity.desc"),
    RELEASE_DATE_ASC("release_date.asc"),
    RELEASE_DATE_DESC("release_date.desc"),
    VOTE_AVERAGE_ASC("vote_average.asc"),
    VOTE_AVERAGE_DESC("vote_average.desc"),
    TITLE_ASC("title.asc"),
    TITLE_DESC("title.desc");

    companion object {
        fun fromString(value: String): SortBy? {
            return SortBy.entries.find { it.value.equals(value, ignoreCase = true) }
        }
    }
}