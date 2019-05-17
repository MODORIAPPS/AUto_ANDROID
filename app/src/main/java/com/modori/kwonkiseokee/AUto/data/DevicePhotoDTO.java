package com.modori.kwonkiseokee.AUto.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DevicePhotoDTO extends RealmObject {

    private String photoID_d;
    private String photoUri_d;

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
