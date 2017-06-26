package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

@AutoValue
public abstract class ResponseGetKeywords {
  public abstract String id();
  @Json(name = "keywords")
  public abstract List<MovieKeyword> results();

  public static JsonAdapter<ResponseGetKeywords> jsonAdapter(Moshi moshi) {
    return new AutoValue_ResponseGetKeywords.MoshiJsonAdapter(moshi);
  }
}