package com.modori.kwonkiseokee.AUto.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "device_photos")
data class DevicePhoto(
        @PrimaryKey @ColumnInfo(name = "photoId") val photoID_d: String,
        @ColumnInfo(name = "photoUri") val photoUri_d:String
)