package com.modori.kwonkiseokee.AUto.Tab1_frag;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "album_table")
public class AlbumDTO implements Serializable {

    @NonNull
    @ColumnInfo(name = "albumTitle")
    private String albumTitle;

    @NonNull
    @ColumnInfo(name = "albumContent")
    private String albumContent;

    @NonNull
    @ColumnInfo(name = "albumDir")
    private String albumDir;

    @ColumnInfo(name = "albumPhotoCnt")
    private int photoCnt;

    @NonNull
    @ColumnInfo(name = "albumIsAvail")
    private boolean isAvail;

    @NonNull
    @ColumnInfo(name = "albumIsPlaying")
    private boolean isPlaying;

    public AlbumDTO(String albumTitle, String albumContent, String albumDir, int photoCnt, boolean isAvail, boolean isPlaying) {
        this.albumTitle = albumTitle;
        this.albumContent = albumContent;
        this.photoCnt = photoCnt;
        this.isAvail = isAvail;
        this.isPlaying = isPlaying;
        this.albumDir = albumDir;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumContent() {
        return albumContent;
    }

    public void setAlbumContent(String albumContent) {
        this.albumContent = albumContent;
    }

    public int getPhotoCnt() {
        return photoCnt;
    }

    public void setPhotoCnt(int photoCnt) {
        this.photoCnt = photoCnt;
    }

    public boolean isAvail() {
        return isAvail;
    }

    public void setAvail(boolean avail) {
        isAvail = avail;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @NonNull
    public String getAlbumDir() {
        return albumDir;
    }

    public void setAlbumDir(@NonNull String albumDir) {
        this.albumDir = albumDir;
    }
}
