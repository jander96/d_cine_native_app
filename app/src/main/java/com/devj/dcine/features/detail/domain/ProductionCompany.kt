package com.devj.dcine.features.detail.domain


data class ProductionCompany(
    val id: Int,
    val logo: String?,
    val name: String,
    val originCountry: String
)