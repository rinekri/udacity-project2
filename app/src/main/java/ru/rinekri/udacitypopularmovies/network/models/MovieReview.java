package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class MovieReview {
  public abstract String id();
  @Json(name = "author")
  public abstract String author();
  @Json(name = "content")
  public abstract String content();
  @Json(name = "url")
  public abstract String url();

  public static JsonAdapter<MovieReview> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieReview.MoshiJsonAdapter(moshi);
  }
}