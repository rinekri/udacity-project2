package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class MovieKeyword {
  public abstract String id();
  public abstract String name();

  public static JsonAdapter<MovieKeyword> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieKeyword.MoshiJsonAdapter(moshi);
  }
}