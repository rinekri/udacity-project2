package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

@AutoValue
public abstract class ResponseGetTitles {
  public abstract String id();
  @Json(name = "titles")
  public abstract List<MovieTitle> results();

  public static JsonAdapter<ResponseGetTitles> jsonAdapter(Moshi moshi) {
    return new AutoValue_ResponseGetTitles.MoshiJsonAdapter(moshi);
  }
}