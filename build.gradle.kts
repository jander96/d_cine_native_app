buildscript {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("io.kotzilla:kotzilla-plugin:1.0.2-RC4")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    id("com.google.protobuf") version "0.9.5" apply false
}