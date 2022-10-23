package me.kavin.piped.utils.obj.search;

public class SearchStream extends SearchItem {

    public String url, title, thumbnail, uploaderName, uploaderUrl, uploaderAvatar, uploadDate, shortDescription;
    public long duration, views, uploaded;
    public boolean uploaderVerified;

    public SearchStream(String url, String title, String thumbnail, String uploaderName, String uploaderUrl,
                      String uploaderAvatar, String uploadedDate, String shortDescription, long duration, long views, long uploaded, boolean uploaderVerified) {
        super(title, thumbnail, url);
        this.url = url;
        this.title = title;
        this.thumbnail = thumbnail;
        this.uploaderName = uploaderName;
        this.uploaderUrl = uploaderUrl;
        this.uploaderAvatar = uploaderAvatar;
        this.uploadDate = uploadedDate;
        this.shortDescription = shortDescription;
        this.duration = duration;
        this.views = views;
        this.uploaded = uploaded;
        this.uploaderVerified = uploaderVerified;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getUploader() {
        return uploaderName;
    }

    public String getUploaderUrl() {
        return uploaderUrl;
    }

    public long getViews() {
        return views;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isUploaderVerified() {
        return uploaderVerified;
    }
    @Override
    public String getInfoType(){
        return "SearchStream";
    }
}
