package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

@AutoValue
public abstract class ResponseGetVideos {
  public abstract String id();
  public abstract List<MovieVideo> results();

  public static JsonAdapter<ResponseGetVideos> jsonAdapter(Moshi moshi) {
    return new AutoValue_ResponseGetVideos.MoshiJsonAdapter(moshi);
  }
}