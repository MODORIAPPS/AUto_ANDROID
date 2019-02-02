package com.example.kwonkiseokee.setwallpaper;

public class dataModel {
    String viewName;
    String viewNum;
    int image_url;


    public dataModel(String viewName, String viewNum, int image_url) {
        this.viewName = viewName;
        this.viewNum = viewNum;
        this.image_url = image_url;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public int getImage_url() {
        return image_url;
    }

    public void setImage_url(int image_url) {
        this.image_url = image_url;
    }
}
