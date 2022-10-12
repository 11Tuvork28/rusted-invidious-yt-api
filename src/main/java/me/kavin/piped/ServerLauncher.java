package me.kavin.piped;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.activej.config.Config;
import io.activej.http.AsyncServlet;
import io.activej.http.HttpMethod;
import io.activej.http.HttpResponse;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import io.activej.inject.module.Module;
import io.activej.launchers.http.MultithreadedHttpServerLauncher;
import me.kavin.piped.consts.Constants;
import me.kavin.piped.utils.*;
import me.kavin.piped.utils.resp.ErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import static io.activej.config.converter.ConfigConverters.ofInetSocketAddress;
import static io.activej.http.HttpHeaders.*;
import static io.activej.http.HttpMethod.GET;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ServerLauncher extends MultithreadedHttpServerLauncher {

    @Provides
    Executor executor() {
        return Multithreading.getCachedExecutor();
    }

    @Provides
    AsyncServlet mainServlet(Executor executor) {

        RoutingServlet router = RoutingServlet.create()
                .map(GET, "/version", AsyncServlet.ofBlocking(executor, request -> getRawResponse(Constants.VERSION.getBytes(UTF_8), "text/plain", "no-store")))
                .map(HttpMethod.OPTIONS, "/*", request -> HttpResponse.ofCode(200))
                .map(GET, "/sponsors/:videoId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(
                                SponsorBlockUtils.getSponsors(request.getPathParameter("videoId"),
                                        request.getQueryParameter("category")).getBytes(UTF_8),
                                "public, max-age=3600");
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/streams/:videoId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(ResponseHelper.streamsResponse(request.getPathParameter("videoId")),
                                "public, s-maxage=21540, max-age=30", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/clips/:clipId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(ResponseHelper.resolveClipId(request.getPathParameter("clipId")),
                                "public, max-age=31536000, immutable");
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/channel/:channelId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(
                                ResponseHelper.channelResponse("channel/" + request.getPathParameter("channelId")),
                                "public, max-age=600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/c/:name", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(ResponseHelper.channelResponse("c/" + request.getPathParameter("name")),
                                "public, max-age=600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/user/:name", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(
                                ResponseHelper.channelResponse("user/" + request.getPathParameter("name")),
                                "public, max-age=600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/nextpage/channel/:channelId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(ResponseHelper.channelPageResponse(request.getPathParameter("channelId"),
                                request.getQueryParameter("nextpage")), "public, max-age=3600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/playlists/:playlistId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        var playlistId = request.getPathParameter("playlistId");
                        var cache = StringUtils.isBlank(playlistId) || playlistId.length() != 36 ?
                                "public, max-age=600" : "private";
                        return getJsonResponse(ResponseHelper.playlistResponse(playlistId), cache, true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/nextpage/playlists/:playlistId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(
                                ResponseHelper.playlistPageResponse(request.getPathParameter("playlistId"),
                                        request.getQueryParameter("nextpage")),
                                "public, max-age=3600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/opensearch/suggestions", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(
                                ResponseHelper.opensearchSuggestionsResponse(request.getQueryParameter("query")),
                                "public, max-age=600");
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/search", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(ResponseHelper.searchResponse(request.getQueryParameter("q"),
                                request.getQueryParameter("filter")), "public, max-age=600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/nextpage/search", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(
                                ResponseHelper.searchPageResponse(request.getQueryParameter("q"),
                                        request.getQueryParameter("filter"), request.getQueryParameter("nextpage")),
                                "public, max-age=3600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/trending", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(ResponseHelper.trendingResponse(request.getQueryParameter("region")),
                                "public, max-age=3600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/comments/:videoId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(ResponseHelper.commentsResponse(request.getPathParameter("videoId")),
                                "public, max-age=1200", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                })).map(GET, "/nextpage/comments/:videoId", AsyncServlet.ofBlocking(executor, request -> {
                    try {
                        return getJsonResponse(ResponseHelper.commentsPageResponse(request.getPathParameter("videoId"),
                                request.getQueryParameter("nextpage")), "public, max-age=3600", true);
                    } catch (Exception e) {
                        return getErrorResponse(e, request.getPath());
                    }
                }));
        return new CustomServletDecorator(router);
    }

    @Override
    protected Module getOverrideModule() {
        return new AbstractModule() {
            @Provides
            Config config() {
                return Config.create()
                        .with("http.listenAddresses",
                                Config.ofValue(ofInetSocketAddress(), new InetSocketAddress(Constants.PORT)))
                        .with("workers", Constants.HTTP_WORKERS);
            }
        };
    }

    private @NotNull HttpResponse getJsonResponse(byte[] body, String cache) {
        return getJsonResponse(200, body, cache, false);
    }

    private @NotNull HttpResponse getJsonResponse(byte[] body, String cache, boolean prefetchProxy) {
        return getJsonResponse(200, body, cache, prefetchProxy);
    }

    private @NotNull HttpResponse getJsonResponse(int code, byte[] body, String cache) {
        return getJsonResponse(code, body, cache, false);
    }

    private @NotNull HttpResponse getJsonResponse(int code, byte[] body, String cache, boolean prefetchProxy) {
        return getRawResponse(code, body, "application/json", cache, prefetchProxy);
    }

    private @NotNull HttpResponse getRawResponse(byte[] body, String contentType, String cache) {
        return getRawResponse(200, body, contentType, cache, false);
    }

    private @NotNull HttpResponse getRawResponse(int code, byte[] body, String contentType, String cache,
                                                 boolean prefetchProxy) {
        HttpResponse response = HttpResponse.ofCode(code).withBody(body).withHeader(CONTENT_TYPE, contentType)
                .withHeader(CACHE_CONTROL, cache);
        if (prefetchProxy)
            response = response.withHeader(LINK, String.format("<%s>; rel=preconnect", Constants.IMAGE_PROXY_PART));
        return response;
    }

    private @NotNull HttpResponse getErrorResponse(Exception e, String path) {

        e = ExceptionHandler.handle(e, path);

        try {
            return getJsonResponse(500, Constants.mapper
                    .writeValueAsBytes(new ErrorResponse(ExceptionUtils.getStackTrace(e), e.getMessage())), "private");
        } catch (JsonProcessingException ex) {
            return HttpResponse.ofCode(500);
        }
    }
}
