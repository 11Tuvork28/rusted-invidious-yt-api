package me.kavin.piped.utils.obj.search;

public class SearchItem {

    private String name, thumbnail, url, type;

    public SearchItem(String name, String thumbnail, String url) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }
    public String getInfoType(){
        return type;
    }
}
