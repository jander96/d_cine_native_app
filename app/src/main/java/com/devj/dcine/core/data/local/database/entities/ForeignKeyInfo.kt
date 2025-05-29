package com.devj.dcine.core.data.local.database.entities

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class ForeignKeyInfo(val referenceTo: KClass<*>,)
