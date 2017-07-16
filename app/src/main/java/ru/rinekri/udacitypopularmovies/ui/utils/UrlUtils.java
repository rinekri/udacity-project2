package ru.rinekri.udacitypopularmovies.ui.utils;

import okhttp3.HttpUrl;
import ru.rinekri.udacitypopularmovies.BuildConfig;

public final class UrlUtils {
  public UrlUtils() {
    throw new RuntimeException("static class");
  }

  public static String contructImageUrl(String imageSize, String relativeUrl) {
    HttpUrl parametrizedBaseUrl = HttpUrl
      .parse(BuildConfig.IMAGE_STORE_BASE_URL)
      .newBuilder()
      .addPathSegments("t/p")
      .addPathSegment(imageSize)
      .build();

    return parametrizedBaseUrl + relativeUrl;
  }
}