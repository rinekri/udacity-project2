package ru.rinekri.udacitypopularmovies.network.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

@AutoValue
public abstract class ResponseGetReviews {
  public abstract String id();
  public abstract int page();
  @Json(name = "total_pages")
  public abstract int totalPages();
  @Json(name = "total_results")
  public abstract int totalResults();
  public abstract List<MovieReview> results();

  public static JsonAdapter<ResponseGetReviews> jsonAdapter(Moshi moshi) {
    return new AutoValue_ResponseGetReviews.MoshiJsonAdapter(moshi);
  }
}