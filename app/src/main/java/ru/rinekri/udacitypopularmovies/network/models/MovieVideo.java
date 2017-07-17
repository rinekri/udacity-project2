package ru.rinekri.udacitypopularmovies.network.models;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import ru.rinekri.udacitypopularmovies.ui.UiConstants;
import ru.rinekri.udacitypopularmovies.ui.utils.LangUtils;

@AutoValue
public abstract class MovieVideo {
  @Json(name = "key")
  public abstract String id();
  @Json(name = "iso_639_1")
  public abstract String lang();
  @Json(name = "name")
  public abstract String name();
  @Json(name = "site")
  public abstract String hostingUrl();

  @NonNull
  public String getYoutubeUrl() {
    LangUtils.check(hostingUrl().equals(UiConstants.VIDEO_YOUTUBE));
    return "https://www.youtube.com/watch?v=".concat(id());
  }

  public static JsonAdapter<MovieVideo> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieVideo.MoshiJsonAdapter(moshi);
  }
}