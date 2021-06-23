package com.ashtiv.myshows;

import java.util.ArrayList;

public class showInfo {
    private String title;
    private String lang;
    private ArrayList<String> authors;
    private String showtype;
    private String premierDate;
    private String description;
    private int runtime;
    private String thumbnail;
    private String previewLink;
    private String infoLink;
    private String buyLink;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPremierDate() {
        return premierDate;
    }

    public void setPremierDate(String premierDate) {
        this.premierDate = premierDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }
    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype;
    }



    public showInfo(String title, String lang, ArrayList<String> authors, String showtype, String premierDate, String description, int runtime, String thumbnail, String previewLink, String infoLink, String buyLink) {
        this.title = title;
        this.lang = lang;
        this.authors = authors;
        this.premierDate = premierDate;
        this.description = description;
        this.runtime = runtime;
        this.showtype=showtype;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
        this.buyLink = buyLink;
    }


}
