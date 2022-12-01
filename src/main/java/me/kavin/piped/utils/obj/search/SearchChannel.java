package me.kavin.piped.utils.obj.search;

public class SearchChannel extends SearchItem {

    private String description, url;
    private long subscribers, videos;
    private boolean verified;

    public SearchChannel(String name, String thumbnail, String url, String description, long subscribers, long videos,
            boolean verified) {
        super(name, thumbnail, url);
        this.description = description;
        this.subscribers = subscribers;
        this.videos = videos;
        this.verified = verified;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public long getSubscribers() {
        return subscribers;
    }

    public long getVideos() {
        return videos;
    }

    public boolean isVerified() {
        return verified;
    }
    public String getUrl() {
        return url;
    }
    @Override
    public String getInfoType(){
        return "SearchChannel";
    }
}
