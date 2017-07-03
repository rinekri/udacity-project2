package ru.rinekri.udacitypopularmovies.network.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class MovieCharacter {
  @Json(name = "cast_id")
  public abstract String id();
  @Json(name = "character")
  public abstract String name();
  @Json(name = "gender")
  public abstract String gender();
  @Json(name = "name")
  public abstract String actorName();
  @Json(name = "profile_path")
  @Nullable
  public abstract String actorPhoto();

  public static JsonAdapter<MovieCharacter> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieCharacter.MoshiJsonAdapter(moshi);
  }
}