package ru.rinekri.udacitypopularmovies.network;

import okhttp3.logging.HttpLoggingInterceptor;
import ru.rinekri.udacitypopularmovies.BuildConfig;

public class NetworkConstants {
  static final Long DEFAULT_CONNECT_TIMEOUT_MS = 10000L;
  static HttpLoggingInterceptor.Level HTTP_LOG_LEVEL;
  static String API_VERSION = "3";
  public static final String BACKDROP_IMAGE_SIZE = "w300";
  public static final String POSTER_IMAGE_SIZE = "w185";

  static {
    if (BuildConfig.DEBUG) {
      HTTP_LOG_LEVEL = HttpLoggingInterceptor.Level.BODY;
    } else {
      HTTP_LOG_LEVEL = HttpLoggingInterceptor.Level.NONE;
    }
  }
}