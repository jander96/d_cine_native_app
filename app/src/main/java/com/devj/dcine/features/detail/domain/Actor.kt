package com.devj.dcine.features.detail.domain

data class Actor(
    val adult: Boolean,
    val gender: Gender,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val castId: Int,
    val character: String,
    val creditId: String,
    val order: Int
)

enum class Gender(val value: Int) {
    MALE(2),
    FEMALE(1);

    companion object {
        fun fromValue(value: Int): Gender {
            return when (value) {
                1 -> FEMALE
                else -> MALE
            }
        }
    }
}