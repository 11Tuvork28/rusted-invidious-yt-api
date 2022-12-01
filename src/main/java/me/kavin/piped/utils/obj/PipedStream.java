package me.kavin.piped.utils.obj;

public class PipedStream {

    public String url, format, quality, mimeType, codec;
    public boolean videoOnly;

    public int bitrate, initStart, initEnd, indexStart, indexEnd, width, height, fps, itag;

    public PipedStream(String url, String format, String quality, String mimeType, boolean videoOnly, int itag) {
        this.url = url;
        this.format = format;
        this.quality = quality;
        this.mimeType = mimeType;
        this.videoOnly = videoOnly;
        this.itag = itag;
    }

    public PipedStream(String url, String format, String quality, String mimeType, boolean videoOnly, int bitrate,
            int initStart, int initEnd, int indexStart, int indexEnd, String codec, int itag) {
        this.url = url;
        this.format = format;
        this.quality = quality;
        this.mimeType = mimeType;
        this.videoOnly = videoOnly;
        this.bitrate = bitrate;
        this.initStart = initStart;
        this.initEnd = initEnd;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.codec = codec;
        this.itag = itag;
    }

    public PipedStream(String url, String format, String quality, String mimeType, boolean videoOnly, int bitrate,
            int initStart, int initEnd, int indexStart, int indexEnd, String codec, int width, int height, int fps, int itag) {
        this.url = url;
        this.format = format;
        this.quality = quality;
        this.mimeType = mimeType;
        this.videoOnly = videoOnly;
        this.bitrate = bitrate;
        this.initStart = initStart;
        this.initEnd = initEnd;
        this.indexStart = indexStart;
        this.indexEnd = indexEnd;
        this.codec = codec;
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.itag = itag;
    }
}
