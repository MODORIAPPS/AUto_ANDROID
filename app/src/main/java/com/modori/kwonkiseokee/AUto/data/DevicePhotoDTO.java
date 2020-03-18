package com.modori.kwonkiseokee.AUto.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Entity(tableName = "device_photos")
public class DevicePhotoDTO extends RealmObject {

    @NonNull
    @ColumnInfo(name = "photoId")
    private String photoID_d = "a";

    @Nonnull
    @ColumnInfo(name = "photoUri")
    private String photoUri_d = "a";

    public DevicePhotoDTO() {

    }

    public DevicePhotoDTO(@NotNull String id, @NotNull String uri) {
        this.photoID_d = id;
        this.photoUri_d = uri;
    }

    public String getPhotoID_d() {
        return photoID_d;
    }

    public void setPhotoID_d(String photoID_d) {
        this.photoID_d = photoID_d;
    }

    public String getPhotoUri_d() {
        return photoUri_d;
    }

    public void setPhotoUri_d(String photoUri_d) {
        this.photoUri_d = photoUri_d;
    }
}
