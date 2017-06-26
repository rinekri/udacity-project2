package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

@AutoValue
public abstract class ResponseGetCharacters {
  public abstract String id();
  @Json(name = "cast")
  public abstract List<MovieCharacter> results();

  public static JsonAdapter<ResponseGetCharacters> jsonAdapter(Moshi moshi) {
    return new AutoValue_ResponseGetCharacters.MoshiJsonAdapter(moshi);
  }
}