package me.kavin.piped;

import io.activej.inject.Injector;
import me.kavin.piped.utils.*;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.localization.ContentCountry;
import org.schabi.newpipe.extractor.localization.Localization;
import org.schabi.newpipe.extractor.services.youtube.YoutubeThrottlingDecrypter;
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {

        NewPipe.init(new DownloaderImpl(), new Localization("en", "US"), ContentCountry.DEFAULT,
                Multithreading.getCachedExecutor());
        YoutubeStreamExtractor.forceFetchAndroidClient(true);
        YoutubeStreamExtractor.forceFetchIosClient(true);

        Injector.useSpecializer();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.printf("ThrottlingCache: %o entries%n", YoutubeThrottlingDecrypter.getCacheSize());
                    YoutubeThrottlingDecrypter.clearCache();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, TimeUnit.MINUTES.toMillis(60));

        try {
            new ServerLauncher().launch(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
