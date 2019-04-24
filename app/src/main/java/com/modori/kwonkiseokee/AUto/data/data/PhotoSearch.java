package com.modori.kwonkiseokee.AUto.data.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoSearch {


    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("total_pages")
    @Expose
    private String total_pages;

    @SerializedName("results")
    @Expose
    private List<Results> results;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
