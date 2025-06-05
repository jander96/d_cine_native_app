package com.devj.dcine.features.settings.domain.models

data class AppSetting(
    val language: String = "en-US",
    val region: String = "US",
    val isDark: Boolean = false,
) {
    val isEmpty: Boolean
        get() {
            return language.isEmpty() && region.isEmpty()
        }
}

