package ru.rinekri.udacitypopularmovies.network.type_adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.ToJson;

import java.lang.annotation.Retention;

import okhttp3.HttpUrl;
import ru.rinekri.udacitypopularmovies.BuildConfig;
import timber.log.Timber;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class PosterUrlAdapter {
  private static final String IMAGE_SIZE = "w185";

  @FromJson
  @Annotation
  String fromJson(String relativePath) {
    HttpUrl parametrizedBaseUrl = HttpUrl
      .parse(BuildConfig.IMAGE_STORE_BASE_URL)
      .newBuilder()
      .addPathSegments("t/p")
      .addPathSegment(IMAGE_SIZE)
      .build();

    String imageUrl = parametrizedBaseUrl + relativePath;
    Timber.e("Image url: %s", imageUrl);
    return imageUrl;
  }

  @ToJson
  String toJson(@Annotation String absolutePath) {
    return absolutePath;
  }

  @Retention(RUNTIME)
  @JsonQualifier
  public @interface Annotation {
  }
}