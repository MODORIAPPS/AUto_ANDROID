package com.modori.kwonkiseokee.AUto.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull


@Entity(tableName = "device_photos")
data class DevicePhotoDTO(
        @PrimaryKey @ColumnInfo(name = "photoId") val photoID_d: String,
        @ColumnInfo(name = "photoUri") val photoUri_d:String
)