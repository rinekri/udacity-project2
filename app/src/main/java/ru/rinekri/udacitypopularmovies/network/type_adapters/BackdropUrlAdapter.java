package ru.rinekri.udacitypopularmovies.network.type_adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.ToJson;

import java.lang.annotation.Retention;

import ru.rinekri.udacitypopularmovies.network.NetworkConstants;
import ru.rinekri.udacitypopularmovies.ui.utils.UrlUtils;
import timber.log.Timber;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class BackdropUrlAdapter {
  @FromJson
  @Annotation
  String fromJson(String relativePath) {
    String imageUrl = UrlUtils.contructImageUrl(NetworkConstants.BACKDROP_IMAGE_SIZE, relativePath);
    Timber.e("Backdrop url: %s", imageUrl);
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