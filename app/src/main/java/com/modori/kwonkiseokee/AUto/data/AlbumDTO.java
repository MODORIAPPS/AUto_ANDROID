package com.modori.kwonkiseokee.AUto.data;

public class AlbumDTO {

    private String albumTitle;
    private String albumContent;
    private int photoCnt;
    private boolean isAvail;
    private boolean isPlaying;

    public AlbumDTO(String albumTitle, String albumContent, int photoCnt, boolean isAvail, boolean isPlaying) {
        this.albumTitle = albumTitle;
        this.albumContent = albumContent;
        this.photoCnt = photoCnt;
        this.isAvail = isAvail;
        this.isPlaying = isPlaying;
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
}
