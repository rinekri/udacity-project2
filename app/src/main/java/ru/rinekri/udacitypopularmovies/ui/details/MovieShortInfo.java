package ru.rinekri.udacitypopularmovies.ui.details;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;

@AutoValue
public abstract class MovieShortInfo implements Parcelable {
  public abstract String id();
  @Nullable
  public abstract String posterUrl();
  @Nullable
  public abstract String backDropUrl();
  public abstract String overview();
  @Nullable
  public abstract String releaseDate();
  public abstract String title();
  public abstract String voteAverage();

  public static MovieShortInfo from(MovieInfo movieInfo) {
    return new AutoValue_MovieShortInfo(
      movieInfo.id(),
      movieInfo.posterUrl(),
      movieInfo.backdropUrl(),
      movieInfo.overview(),
      movieInfo.releaseDate(),
      movieInfo.title(),
      movieInfo.voteAverage()
    );
  }
}