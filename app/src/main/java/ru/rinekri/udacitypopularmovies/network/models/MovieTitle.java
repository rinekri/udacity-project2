package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class MovieTitle {
  @Json(name = "iso_3166_1")
  public abstract String lang();
  @Json(name = "title")
  public abstract String name();

  public String getShortName() {
    return (name().length() <= 10 ? name() : name().substring(0, 10)).concat("...");
  }

  public static JsonAdapter<MovieTitle> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieTitle.MoshiJsonAdapter(moshi);
  }
}