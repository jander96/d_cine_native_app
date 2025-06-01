package com.devj.dcine.core.data.local.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.devj.dcine.AppSettingProto
import com.devj.dcine.features.settings.domain.models.AppSetting
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

private const val PREFERENCES_SETTINGS = "settings"
private const val PROTO_FILENAME = "settings.pb"



val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_SETTINGS)

object AppSettingSerializer : Serializer<AppSettingProto> {
    override val defaultValue: AppSettingProto
        get() = AppSettingProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppSettingProto {
        try {
            return AppSettingProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: AppSettingProto,
        output: OutputStream
    ) {
        t.writeTo(output)
    }

}

val Context.settingsDataStore: DataStore<AppSettingProto> by dataStore(
    fileName = PROTO_FILENAME,
    serializer = AppSettingSerializer
)