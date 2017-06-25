package ru.rinekri.udacitypopularmovies.network.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

import ru.rinekri.udacitypopularmovies.network.type_adapters.BackdropUrlAdapter;
import ru.rinekri.udacitypopularmovies.network.type_adapters.PosterUrlAdapter;

@AutoValue
public abstract class MovieInfo implements Parcelable {
  public abstract Long id();
  @Json(name = "poster_path")
  @PosterUrlAdapter.Annotation
  public abstract String posterUrl();
  public abstract Boolean adult();
  public abstract String overview();
  @Json(name = "release_date")
  public abstract String releaseDate();
  @Json(name = "genre_ids")
  public abstract List<Integer> genreIds();
  @Json(name = "original_title")
  public abstract String originalTitle();
  public abstract String title();
  @Json(name = "original_language")
  public abstract String originalLanguage();
  @Json(name = "backdrop_path")
  @BackdropUrlAdapter.Annotation
  public abstract String backdropUrl();
  public abstract String popularity();
  @Json(name = "vote_count")
  public abstract String voteCount();
  public abstract Boolean video();
  @Json(name = "vote_average")
  public abstract String voteAverage();

  public static JsonAdapter<MovieInfo> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieInfo.MoshiJsonAdapter(moshi);
  }
}