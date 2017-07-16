package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class MovieVideo {
  public abstract String id();
  @Json(name = "iso_639_1")
  public abstract String lang();
  @Json(name = "name")
  public abstract String name();
  @Json(name = "site")
  public abstract String service();

  public static JsonAdapter<MovieVideo> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieVideo.MoshiJsonAdapter(moshi);
  }
}