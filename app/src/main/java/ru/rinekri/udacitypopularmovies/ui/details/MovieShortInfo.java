package ru.rinekri.udacitypopularmovies.ui.details;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;

import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.type_adapters.PosterUrlAdapter;

@AutoValue
public abstract class MovieShortInfo implements Parcelable {
  @Json(name = "poster_path")
  @PosterUrlAdapter.Annotation
  public abstract String posterPath();
  public abstract String overview();
  @Json(name = "release_date")
  public abstract String releaseDate();
  @Json(name = "original_title")
  public abstract String title();
  @Json(name = "vote_average")
  public abstract String voteAverage();

  public static MovieShortInfo from(MovieInfo movieInfo) {
    return new AutoValue_MovieShortInfo(
      movieInfo.posterUrlSmall(),
      movieInfo.overview(),
      movieInfo.releaseDate(),
      movieInfo.title(),
      movieInfo.voteAverage()
    );
  }
}